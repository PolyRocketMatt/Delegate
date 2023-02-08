package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
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
    public boolean registerCommand(CommandNode node) {
        for (CommandNode root : commandTree.getRoots()) {
            try {
                registerCommand(node, root, null);
            } catch (CommandExecutionException ex) {
                ex.printStackTrace();

                //  If any exception occurred, we did not successfully register the command
                return false;
            }
        }

        return true;
    }

    private void registerCommand(CommandNode node, CommandNode match, CommandNode parent) throws CommandRegisterException {
        //  We perform a "match check", checking if the node and match have the same definitions
        if (match(node, match)) {
            //  If the match is not verified, we cannot safely replace it, and we must throw an exception
            if (!match.isVerified())
                throw new CommandRegisterException("Cannot replace unverified command node with verified command node");
            VerifiedDelegateCommand rootCommand = (VerifiedDelegateCommand) match.getCommand();

            //  If the match has a command with no actions, excepts or triggers, we can safely replace it
            boolean hasActions = rootCommand.getActionBuffer() != null && !(rootCommand.getActionBuffer().size() == 0);
            boolean hasExcepts = rootCommand.getExceptBuffer() != null && !(rootCommand.getExceptBuffer().size() == 0);
            boolean hasTriggers = rootCommand.getTriggerBuffer() != null && !(rootCommand.getTriggerBuffer().size() == 0);

            if (!hasActions && !hasExcepts && !hasTriggers)
                match.setCommand(node.getCommand());
            else
                throw new CommandRegisterException("Cannot replace command node with verified command node that has actions, excepts or triggers");

            //  For each child, we must now match check against the match's children
            for (CommandNode childNode : node.getChildren())
                for (CommandNode childMatch : match.getChildren())
                    registerCommand(childNode, childMatch, match);
        } else {
            //  If the current parent is null, we're at the roots of the tree. This means we can just add the node as a root
            if (parent == null) {
                this.commandTree.add(node);

                //  Since we're adding a root, we must register the command with the platform
                //  We only need to do this for the root, as the children will be "registered" when they are added
                getDelegate().getPlatform().registerToPlatform(node.getCommand());
            }

            //  Otherwise, we must add the node as a child of the parent
            else
                parent.addChild(node);
        }
    }

    private boolean match(CommandNode left, CommandNode right) {
        return left.getNameDefinition().getValue().equals(right.getNameDefinition().getValue());
    }

    /**
     * Handles the given {@link CommandDispatchInformation} and tries to execute the
     * command associated with the information.
     *
     * @param information The {@link CommandDispatchInformation} to handle.
     * @return True if the information was handled successfully, false otherwise.
     * @throws CommandExecutionException If an error occurred while parsing the information.
     */
    //  TODO: Add default execution routines on exception against argument parsing
    @Override
    public boolean handle(CommandDispatchInformation information) throws CommandExecutionException {
        CommanderEntity commander = information.commander();
        String commandName = information.command();
        String[] commandArguments = information.arguments();
        boolean safeExecuteTopLevel = getDelegate().verbose();

        //  Parse information arguments until command in root node doesn't exist
        CommandNode root = this.commandTree.find(commandName);

        //  If the root is null, the command doesn't exist
        if (root == null)
            return false;

        QueryResultNode queryResultNode = root.findDeepest(commandName, commandArguments);
        CommandNode executionNode = queryResultNode.node();
        String matchedCommandPattern = queryResultNode.commandPattern();

        //  Check if this is
        if (executionNode == null)
            if (safeExecuteTopLevel) {
                try {
                    throw exceptOrThrow(information, null, FeedbackType.COMMAND_NON_EXISTENT, matchedCommandPattern);
                } catch (Exception ex) {
                    return generateEventFromException(information, ex);
                }
            } else
                return false;

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

    private String[] verifyArguments(CommandDispatchInformation information, VerifiedDelegateCommand command, String[] arguments) {
        CommandBuffer<CommandProperty> commandProperties = command.getPropertyBuffer();
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();
        String[] verifiedArguments = new String[arguments.length];

        //  Check properties
        boolean ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);
        boolean ignoreNonPresent = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNonPresentProperty);

        //  Check argument counts
        if (commandArguments.size() > arguments.length && !ignoreNonPresent)
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
                verifiedArguments[argumentIndex] = parts[1];
                isAssigmentOperator++;
            } else {
                if (isAssigmentOperator != 0)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_FORMAT, argument);
                else
                    verifiedArguments[i] = argument;
            }
        }

        //  Check that all arguments have been successfully parsed
        if (!ignoreNull)
            for (int i = 0; i < verifiedArguments.length; i++) {
                if (verifiedArguments[i] == null)
                    throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_FORMAT, arguments[i]);
            }

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
                throw exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_PARSE_RESULT, argument, ex.getParseType().getName());
            }
        }

        return parsedArguments;
    }

}
