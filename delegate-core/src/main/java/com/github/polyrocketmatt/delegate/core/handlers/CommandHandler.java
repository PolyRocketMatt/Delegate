package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.action.FunctionAction;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.QueryResultNode;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.exception.CommandExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class CommandHandler implements Handler {

    private final CommandTree commandTree;

    private final int availableProcessors;
    private final int maxStealCount;

    //  TODO: Fix a configuration for max available processors
    public CommandHandler() {
        this.commandTree = new CommandTree();
        this.availableProcessors = 32;
        this.maxStealCount = 8;
    }

    public void registerTree(CommandNode root) {
        this.commandTree.add(root);
    }

    public boolean handle(CommandDispatchInformation information) throws CommandExecutionException {
        String commandName = information.command();
        String[] commandArguments = information.arguments();

        //  Parse information arguments until command in root node doesn't exist
        CommandNode root = this.commandTree.find(commandName);
        QueryResultNode queryResultNode = root.findDeepest(commandArguments);
        CommandNode executionNode = queryResultNode.node();

        //  Check if the command is verified
        if (!executionNode.isVerified())
            throw new CommandExecutionException("Expected to execute a verified command, found an attributed command instead");

        //  We can then parse the remaining arguments, apply rules to them and parse them.
        String[] remainingArguments = queryResultNode.remainingArgs();
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) executionNode.getCommand();
        String[] verifiedArguments = this.verifyArguments(command, remainingArguments);

        //  We can execute the command with the remaining arguments

        return true;
    }

    private String[] verifyArguments(VerifiedDelegateCommand command, String[] arguments) {
        CommandBuffer<CommandProperty> commandProperties = command.getPropertyBuffer();
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();
        String[] verifiedArguments = new String[arguments.length];

        //  Check argument types
        int isAssigmentOperator = 0;
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            String[] parts = argument.split("=");

            if (parts.length == 2) {
                //  Find the argument index in the argument buffer
                int argumentIndex = commandArguments.indexWhere(arg -> arg.getIdentifier().equals(parts[0]));
                if (argumentIndex == -1)
                    throw new CommandExecutionException("Argument %s does not exist in command %s"
                            .formatted(parts[0], command.getNameDefinition().getIdentifier()));
                verifiedArguments[argumentIndex] = parts[1];
                isAssigmentOperator++;
            } else {
                if (isAssigmentOperator != 0)
                    throw new CommandExecutionException("Expected assignment operator '=' for all arguments");
                else
                    verifiedArguments[i] = argument;
            }
        }

        //  Check ignore null property
        boolean ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);

        //  Check that all arguments have been successfully parsed
        if (!ignoreNull)
            for (int i = 0; i < verifiedArguments.length; i++) {
                if (verifiedArguments[i] == null)
                    throw new CommandExecutionException("Argument %s was not parsed successfully or was not found"
                            .formatted(commandArguments.get(i).getIdentifier()));
            }

        //  Parse all argument rules
        for (int i = 0; i < verifiedArguments.length; i++) {
            CommandArgument<?> commandArgument = commandArguments.get(i);
            String argument = verifiedArguments[i];

            commandArgument.parseRules(argument);
        }

        return verifiedArguments;
    }

    private Map<String, ActionResult> execute(VerifiedDelegateCommand command, String[] arguments) {
        //  Get the arguments
        CommandBuffer<CommandArgument<?>> commandArguments = command.getArgumentBuffer();

        //  Run all command actions in order of precedence
        CommandBuffer<FunctionAction> actions = command.getActionBuffer();
        List<Integer> precedences = actions.stream()
                .map(FunctionAction::getPrecedence)
                .sorted()
                .toList();

        //  Check async-property and initialize parameters
        boolean async = command.getPropertyBuffer().stream()
                .anyMatch(property -> property instanceof AsyncProperty);
        int availableThreadCount = Math.min(this.availableProcessors, this.maxStealCount);
        int threadCount = Math.min(availableThreadCount, actions.size());

        //  Verification of arguments ensures correct order of arguments
        List<String> inputs = List.of(arguments);
        Map<String, ActionResult> results = new HashMap<>();
        ExecutorService executor = new ForkJoinPool(threadCount);

        for (int precedence : precedences) {
            List<FunctionAction> actionsWithPrecedence = actions.stream()
                    .filter(action -> action.getPrecedence() == precedence)
                    .toList();

            if (async) {
                for (FunctionAction action : actionsWithPrecedence)
                        executor.execute(() -> results.put(action.getIdentifier(), action.run(commandArguments, inputs)));
            } else {
                for (FunctionAction action : actionsWithPrecedence)
                    results.put(action.getIdentifier(), action.run(commandArguments, inputs));
            }
        }

        return results;
    }

    public boolean dispatch(VerifiedDelegateCommand command, CommandDispatchInformation information) {
        return true;
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.commandTree.clear();
    }

}
