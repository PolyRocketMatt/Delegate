// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CatchExceptionProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.QueryResultNode;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * Handler that is responsible for processing and dispatching commands.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class InternalCommandHandler extends DelegateCommandHandler {

    private final CommandTree commandTree;

    /**
     * Creates a new {@link InternalCommandHandler} instance.
     */
    //  TODO: Add a configuration for max available processors
    public InternalCommandHandler() {
        super(32, 8);
        this.commandTree = new CommandTree();
    }

    public CommandTree getCommandTree() {
        return commandTree;
    }

    /**
     * Adds a {@link CommandNode} to the command tree structure.
     *
     * @param node The {@link CommandNode} to add.
     */
    public boolean registerCommand(CommandNode node) throws CommandRegisterException {
        insertIntoTree(node, node.getParent());

        return true;
    }

    private void insertIntoTree(CommandNode node, CommandNode level) throws CommandRegisterException {
        //  We check the current node against the current parent's sub-commands
        if (level == null) {
            //  If the parent is null, we're just checking the root nodes
            if (this.commandTree.getRoots().stream().anyMatch(rootNode -> match(node, rootNode)))
                throw new CommandRegisterException("Cannot overwrite command node with the same name: %s".formatted(node.getNameDefinition().getValue()));
            else {
                this.commandTree.add(node);

                //  Since we're adding a root, we must register the command with the platform
                registerToServer(node.getCommand());
            }
        }
    }

    private void registerToServer(IDelegateCommand command) {
        getDelegate().getPlatform().registerToPlatform(command);
        getDelegate().getPlatform().registerToPlayers(command);
    }

    private boolean match(CommandNode left, CommandNode right) {
        return left.getNameDefinition().getValue().equals(right.getNameDefinition().getValue());
    }

    /**
     * Handles the given {@link CommandDispatchInformation} and tries to execute the
     * command associated with the information.
     *
     * @param commandDispatchInformation The {@link CommandDispatchInformation} to handle.
     * @return True if the information was handled successfully, false otherwise.
     * @throws CommandExecutionException If an error occurred while parsing the information.
     */
    //  TODO: Add default execution routines on exception against argument parsing
    @Override
    public boolean handle(CommandDispatchInformation commandDispatchInformation) throws CommandExecutionException {
        CommandDispatchInformation information = commandDispatchInformation;
        CommanderEntity commander = information.commander();
        String commandName = information.command();
        String[] commandArguments = information.arguments();
        boolean safeExecuteTopLevel = getDelegate().isVerbose();

        //  Parse information arguments until command in root node doesn't exist
        CommandNode root = this.commandTree.find(commandName);

        //  If the root is null, the command doesn't exist
        if (root == null)
            return generateEventFromException(information, exceptOrThrow(information, null, FeedbackType.COMMAND_NON_EXISTENT, commandName));

        //  Parse in arguments to find the deepest node
        QueryResultNode queryResultNode = root.findDeepest(commandName, commandArguments);
        CommandNode executionNode = queryResultNode.node();
        String matchedCommandPattern = queryResultNode.commandPattern();

        //  Check that the execution node is the actual intended command
        //  TODO: Refactor this...
        if (!executionNode.getNameDefinition().getValue().equals(commandName))
            return generateEventFromException(information, exceptOrThrow(information, null, FeedbackType.COMMAND_NON_EXISTENT, commandName));

        //  Check if the command is verified
        if (!executionNode.isVerified())
            if (safeExecuteTopLevel) {
                try {
                    throw exceptOrThrow(information, null, FeedbackType.COMMAND_UNVERIFIED, matchedCommandPattern);
                } catch (Exception ex) { return generateEventFromException(information, ex); }
            } else
                return false;

        //  We can then parse the remaining arguments, apply rules to them and parse them.
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) executionNode.getCommand();

        //  Check if the command is executed safely
        boolean safeExecute = command.getPropertyBuffer().stream()
                .anyMatch(property -> property instanceof CatchExceptionProperty);

        try {
            String[] remainingArguments = queryResultNode.remainingArgs();

            //  Check if the provided command has too many arguments
            //  If this is the case, we tried executing a non-existent command
            if (command.getArgumentBuffer().size() < remainingArguments.length)
                throw exceptOrThrow(information, command, FeedbackType.COMMAND_NON_EXISTENT, matchedCommandPattern + " " + String.join(" ", remainingArguments));

            //  Before argument verification, we combine string arguments
            information = this.combineStringArguments(information);

            //  Verify arguments
            String[] verifiedArguments = this.verifyArguments(information, command, remainingArguments);
            List<Argument<?>> parsedArguments = this.parseArguments(information, command, verifiedArguments);

            //  Check if the commander has permission to execute the command
            if (!canExecute(information.commander(), command.getPermissionBuffer()))
                throw exceptOrThrow(information, command, FeedbackType.UNAUTHORIZED, matchedCommandPattern);

            CommandBuffer<CommandAction> actions = command.getActionBuffer();
            boolean async = command.getPropertyBuffer().stream()
                    .anyMatch(property -> property instanceof AsyncProperty);

            //  We can execute the command with the remaining arguments
            List<CommandCapture.Capture> captures = this.execute(commander, actions, parsedArguments, async);
            CommandCapture capture = new CommandCapture(captures);

            //  Execute triggers
            this.executeTriggers(information, command, capture);

            //  Call event for other plugins possibly?
            return getDelegate().getPlatform().dispatch(information, capture);
        } catch (CommandExecutionException ex) {
            if (safeExecute)
                return generateEventFromException(information, ex);

            //  Inform the commander of the error
            commander.sendMessage(ex.getFeedback());
        }

        return false;
    }

    public List<String> findCompletions(String[] matched) {
        String commandName = matched[0];
        String[] commandArguments = Arrays.copyOfRange(matched, 1, matched.length);
        CommandNode root = this.commandTree.find(commandName);

        //  If the root is null, the command doesn't exist
        if (root == null)
            return List.of();

        //  The matched node is the closest we got to the command
        QueryResultNode queryResultNode = root.findDeepest(commandName, commandArguments);
        CommandNode matchedNode = queryResultNode.node();

        //  If the matched node is null, we can't complete
        if (matchedNode == null)
            return List.of();

        List<String> childCommandNames = matchedNode.getChildren().stream()
                .map(node -> node.getNameDefinition().getValue())
                .toList();

        //  If there is an argument that isn't empty
        if (Arrays.stream(queryResultNode.remainingArgs()).anyMatch(arg -> !arg.isEmpty())) {
            //  Try to match children as good as possible
            List<String> childMatches = new ArrayList<>();

            for (String childName : childCommandNames)
                if (childName.startsWith(queryResultNode.remainingArgs()[0]))
                    childMatches.add(childName);

            return childMatches;
        }

        //  We can now return the names of the children
        return childCommandNames;
    }

    private boolean canExecute(CommanderEntity commander, CommandBuffer<PermissionTier> permissionTiers) {
        if (permissionTiers.size() == 0)
            return true;
        return permissionTiers.stream().anyMatch(tier -> tier.hasPermission(commander));
    }

    private CommandDispatchInformation combineStringArguments(CommandDispatchInformation information) {
        List<String> combinedStringArguments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (String argument : information.arguments()) {
            if (argument.startsWith("\"") && !argument.endsWith("\"")) {
                buffer.append(argument.substring(1)).append(" ");

                continue;
            }

            if (argument.endsWith("\"") && !argument.startsWith("\"")) {
                buffer.append(argument, 0, argument.length() - 1);
                combinedStringArguments.add(buffer.toString());
                buffer = new StringBuilder();

                continue;
            }

            if (argument.startsWith("\"") && argument.endsWith("\"")) {
                combinedStringArguments.add(argument.substring(1, argument.length() - 1));

                continue;
            }

            combinedStringArguments.add(argument);
        }

        return new CommandDispatchInformation(information.commander(), information.command(), combinedStringArguments.toArray(String[]::new));
    }

    private String[] verifyArguments(CommandDispatchInformation information, VerifiedDelegateCommand command, String[] arguments) {
        CommandBuffer<CommandProperty> commandProperties = command.getPropertyBuffer();
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();
        String[] verifiedArguments = new String[arguments.length];

        //  Check properties
        boolean ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);
        boolean ignoreNonPresent = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNonPresentProperty);

        //  Check argument counts
        if (commandArguments.size() > arguments.length && !ignoreNonPresent && !ignoreNull)
            throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_COUNT, commandArguments.size(), arguments.length);

        //  Check argument types
        int isAssigmentOperator = 0;
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            String[] parts = argument.split("=", 2);

            if (parts.length == 2) {
                //  Find the argument index in the argument buffer
                int argumentIndex = commandArguments.indexWhere(arg -> arg.getIdentifier().equals(parts[0]));
                if (argumentIndex == -1)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_IDENTIFIER, parts[0]);

                //  Check if the argument was already parsed
                if (verifiedArguments[argumentIndex] != null)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_DUPLICATE_IDENTIFIER, parts[0]);

                verifiedArguments[argumentIndex] = parts[1];
                isAssigmentOperator++;
            } else {
                if (isAssigmentOperator != 0)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_FORMAT,
                            Objects.requireNonNullElse(commandArguments.get(i).getIdentifier(), "unknown"), "=", " ");
                else
                    verifiedArguments[i] = argument;
            }
        }

        //  Check that all arguments have been successfully parsed
        if (!ignoreNull)
            for (int i = 0; i < verifiedArguments.length; i++)
                if (verifiedArguments[i] == null)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_TYPE, commandArguments.get(i).getIdentifier(), arguments[i]);

        //  Parse all argument rules, the index should be equal to the amount of command arguments
        //  except if the ignore non-present property is set.
        int maxIndex = Math.min(verifiedArguments.length, commandArguments.size());
        for (int i = 0; i < maxIndex; i++) {
            CommandArgument<?> commandArgument = commandArguments.get(i);
            String argument = verifiedArguments[i];

            commandArgument.parseRules(argument);
        }

        return verifiedArguments;
    }

    private List<Argument<?>> parseArguments(CommandDispatchInformation information, VerifiedDelegateCommand command, String[] arguments) {
        List<Argument<?>> parsedArguments = new ArrayList<>();
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();

        //  Parse all arguments
        for (int i = 0; i < arguments.length; i++) {
            CommandArgument<?> commandArgument = commandArguments.get(i);
            String argument = arguments[i];

            try {
                parsedArguments.add(commandArgument.parse(argument));
            } catch (ArgumentParseException ex) {
                throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_PARSE_RESULT, Objects.requireNonNullElse(argument, "null"), ex.getParseType().getName());
            }
        }

        return parsedArguments;
    }

    @Override
    public void clearCommandCache() {
        this.commandTree.clear();
    }
}
