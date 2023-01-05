package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
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
public class CommandHandler implements IHandler {

    private final CommandTree commandTree;

    private final int availableProcessors;
    private final int maxStealCount;

    /**
     * Creates a new {@link CommandHandler} instance.
     */
    //  TODO: Fix a configuration for max available processors
    public CommandHandler() {
        this.commandTree = new CommandTree();
        this.availableProcessors = 32;
        this.maxStealCount = 8;
    }

    private CommandExecutionException createException(CommandDispatchInformation information, FeedbackType type, Object... args) {
        return new CommandExecutionException(information, getDelegate().getConfiguration().get(type), type, args);
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
            throw createException(information, FeedbackType.COMMAND_NOT_FOUND, commandName);

        QueryResultNode queryResultNode = root.findDeepest(commandArguments);
        CommandNode executionNode = queryResultNode.node();

        //  Check if the command is verified & non-null
        if (executionNode == null)
            throw createException(information, FeedbackType.COMMAND_NOT_FOUND, commandName);
        if (!executionNode.isVerified())
            throw createException(information, FeedbackType.COMMAND_UNVERIFIED, commandName);

        //  We can then parse the remaining arguments, apply rules to them and parse them.
        String[] remainingArguments = queryResultNode.remainingArgs();
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) executionNode.getCommand();
        String[] verifiedArguments = this.verifyArguments(information, command, remainingArguments);

        System.out.println("Listing command dispatch information:");
        System.out.println("Commander: " + commander.getClass().getName());
        System.out.println("Command: " + commandName);
        System.out.println("Arguments: " + String.join(", ", commandArguments));
        System.out.println("Remaining arguments: " + String.join(", ", remainingArguments));
        System.out.println("Verified arguments: " + String.join(", ", verifiedArguments));
        System.out.println("Expected arguments: " + command.getArgumentBuffer().size());

        List<Argument<?>> parsedArguments = this.parseArguments(information, command, verifiedArguments);

        /**
        //  We can execute the command with the remaining arguments
        List<CommandCapture.Capture> captures = this.execute(commander, command, verifiedArguments);
        CommandCapture capture = new CommandCapture(captures);
         */

        return true; //getDelegate().getPlatform().dispatch(information, capture);
    }

    private String[] verifyArguments(CommandDispatchInformation information, VerifiedDelegateCommand command, String[] arguments) {
        CommandBuffer<CommandProperty> commandProperties = command.getPropertyBuffer();
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();
        String[] verifiedArguments = new String[arguments.length];

        //  TODO: STRING MATCHING

        //  Check properties
        boolean ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);
        boolean ignoreNonPresent = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNonPresentProperty);

        //  Check argument counts
        if (commandArguments.size() > arguments.length && !ignoreNonPresent)
            throw createException(information, FeedbackType.ARGS_INVALID_COUNT, commandArguments.size(), arguments.length);

        //  Check argument types
        int isAssigmentOperator = 0;
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            String[] parts = argument.split("=", 2);

            if (parts.length == 2) {
                //  Find the argument index in the argument buffer
                int argumentIndex = commandArguments.indexWhere(arg -> arg.getIdentifier().equals(parts[0]));
                if (argumentIndex == -1)
                    throw createException(information, FeedbackType.ARGS_INVALID_IDENTIFIER, parts[0]);
                verifiedArguments[argumentIndex] = parts[1];
                isAssigmentOperator++;
            } else {
                if (isAssigmentOperator != 0)
                    throw createException(information, FeedbackType.ARGS_INVALID_FORMAT, argument);
                else
                    verifiedArguments[i] = argument;
            }
        }

        //  Check that all arguments have been successfully parsed
        if (!ignoreNull)
            for (int i = 0; i < verifiedArguments.length; i++) {
                if (verifiedArguments[i] == null)
                    throw createException(information, FeedbackType.ARGS_INVALID_FORMAT, arguments[i]);
            }

        //  Parse all argument rules
        for (int i = 0; i < verifiedArguments.length; i++) {
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
                throw createException(information, FeedbackType.ARGS_INVALID_PARSE_RESULT, argument, ex.getParseType().getName());
            }
        }

        return parsedArguments;
    }

    private List<CommandCapture.Capture> execute(CommanderEntity commander, VerifiedDelegateCommand command, List<Argument<?>> arguments) {
        //  Get the arguments
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();

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
                        executor.execute(() -> captures.add(
                                new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments))));
            } else {
                for (CommandAction action : actionsWithPrecedence)
                    captures.add(
                            new CommandCapture.Capture(action.getIdentifier(), action.run(commander, arguments)));
            }
        }

        return captures;
    }

    /**
     * Dispatches the given {@link VerifiedDelegateCommand} with the given {@link CommandDispatchInformation}.
     *
     * @param command The {@link VerifiedDelegateCommand} to dispatch.
     * @param information The {@link CommandDispatchInformation} to dispatch with.
     * @return True if the command was dispatched successfully, false otherwise.
     */
    public boolean dispatch(VerifiedDelegateCommand command, CommandDispatchInformation information) {
        return true;
    }

}
