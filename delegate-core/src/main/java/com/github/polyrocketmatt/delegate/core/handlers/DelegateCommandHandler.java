package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNonPresentProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.QueryResultNode;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * Handler that is responsible for processing and dispatching commands.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DelegateCommandHandler implements IHandler {

    private final CommandTree commandTree;

    private final int availableProcessors;
    private final int maxStealCount;

    /**
     * Creates a new {@link DelegateCommandHandler} instance.
     */
    //  TODO: Fix a configuration for max available processors
    public DelegateCommandHandler() {
        this.commandTree = new CommandTree();
        this.availableProcessors = 32;
        this.maxStealCount = 8;
    }

    public CommandTree getCommandTree() {
        return commandTree;
    }

    private boolean exceptOrThrow(CommandDispatchInformation information, VerifiedDelegateCommand cmd, FeedbackType type, Object... args)
            throws CommandExecutionException {
        if (cmd != null) {
            CommandBuffer<ExceptAction> actions = cmd.getExceptBuffer();

            if (actions != null) {
                for (ExceptAction action : actions)
                    action.run(information.commander(), type, Arrays.asList(information.arguments()));
                return true;
            }
        }

        throw new CommandExecutionException(information, getDelegate().getConfiguration().get(type), type, args);
    }

    /**
     * Adds a {@link CommandNode} as root to the command tree.
     *
     * @param root The root {@link CommandNode} to add.
     */
    public void registerTree(CommandNode root) {
        this.commandTree.add(root);

        getDelegate().getPlatform().register(root.getCommand());
    }

    /**
     * Handles the given {@link CommandDispatchInformation} and tries to execute the
     * command associated with the information.
     *
     * @param information The {@link CommandDispatchInformation} to handle.
     * @return True if the information was handled successfully, false otherwise.
     * @throws CommandExecutionException If an error occurred while parsing the information.
     */
    public boolean handle(CommandDispatchInformation information) throws CommandExecutionException {
        CommanderEntity commander = information.commander();
        String commandName = information.command();
        String[] commandArguments = information.arguments();

        //  Parse information arguments until command in root node doesn't exist
        CommandNode root = this.commandTree.find(commandName);
        if (root == null)
            exceptOrThrow(information, null, FeedbackType.COMMAND_NOT_FOUND, commandName);

        QueryResultNode queryResultNode = root.findDeepest(commandArguments);
        CommandNode executionNode = queryResultNode.node();

        //  Check if the command is verified & non-null
        if (executionNode == null)
            exceptOrThrow(information, null, FeedbackType.COMMAND_NOT_FOUND, commandName);
        if (!executionNode.isVerified())
            exceptOrThrow(information, null, FeedbackType.COMMAND_UNVERIFIED, commandName);

        //  We can then parse the remaining arguments, apply rules to them and parse them.
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) executionNode.getCommand();
        String[] remainingArguments = queryResultNode.remainingArgs();
        String[] verifiedArguments = this.verifyArguments(information, command, remainingArguments);
        List<Argument<?>> parsedArguments = this.parseArguments(information, command, verifiedArguments);

        //  Check if the commander has permission to execute the command
        if (!canExecute(information.commander(), command.getPermissionBuffer()))
            if (exceptOrThrow(information, command, FeedbackType.UNAUTHORIZED, commandName))
                return true;

        //  We can execute the command with the remaining arguments
        List<CommandCapture.Capture> captures = this.execute(commander, command, parsedArguments);
        CommandCapture capture = new CommandCapture(captures);

        //  Execute triggers
        this.executeTriggers(information, command, capture);

        //  Call event for other plugins possibly?
        return getDelegate().getPlatform().dispatch(information, capture);
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
            exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_COUNT, commandArguments.size(), arguments.length);

        //  Check argument types
        int isAssigmentOperator = 0;
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            String[] parts = argument.split("=", 2);

            if (parts.length == 2) {
                //  Find the argument index in the argument buffer
                int argumentIndex = commandArguments.indexWhere(arg -> arg.getIdentifier().equals(parts[0]));
                if (argumentIndex == -1)
                    exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_IDENTIFIER, parts[0]);
                verifiedArguments[argumentIndex] = parts[1];
                isAssigmentOperator++;
            } else {
                if (isAssigmentOperator != 0)
                    exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_FORMAT, argument);
                else
                    verifiedArguments[i] = argument;
            }
        }

        //  Check that all arguments have been successfully parsed
        if (!ignoreNull)
            for (int i = 0; i < verifiedArguments.length; i++) {
                if (verifiedArguments[i] == null)
                    exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_FORMAT, arguments[i]);
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
                exceptOrThrow(information, command, FeedbackType.ARGS_INVALID_PARSE_RESULT, argument, ex.getParseType().getName());
            }
        }

        return parsedArguments;
    }

    private List<CommandCapture.Capture> execute(CommanderEntity commander, VerifiedDelegateCommand command, List<Argument<?>> arguments) {
        //  Run all command actions in order of precedence
        CommandBuffer<CommandAction> actions = command.getActionBuffer();
        List<Integer> precedences = actions.stream()
                .map(CommandAction::getPrecedence)
                .sorted()
                .toList();

        //  Check async-property and initialize parameters
        boolean async = command.getPropertyBuffer().stream()
                .anyMatch(property -> property instanceof AsyncProperty);
        int availableThreadCount = Math.min(this.availableProcessors, this.maxStealCount);
        int threadCount = Math.min(availableThreadCount, actions.size());

        //  Verification of arguments ensures correct order of arguments
        List<CommandCapture.Capture> captures = new ArrayList<>();
        ExecutorService executor = new ForkJoinPool(threadCount);

        for (int precedence : precedences) {
            List<CommandAction> actionsWithPrecedence = actions.stream()
                    .filter(action -> action.getPrecedence() == precedence)
                    .toList();

            if (async) {
                for (CommandAction action : actionsWithPrecedence)
                        executor.execute(() -> captures.add(new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments))));
            } else {
                for (CommandAction action : actionsWithPrecedence)
                    captures.add(new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments)));
            }
        }

        return captures;
    }

    private void executeTriggers(CommandDispatchInformation information, VerifiedDelegateCommand command, CommandCapture capture) {
        CommandBuffer<CommandTrigger> triggers = command.getTriggerBuffer();
        List<ActionItem.Result> results = capture.getResults();

        //  TODO: Async triggers?

        triggers.forEach(trigger -> {
            if (trigger.shouldTrigger(results))
                trigger.call(information, capture);
        });
    }

}
