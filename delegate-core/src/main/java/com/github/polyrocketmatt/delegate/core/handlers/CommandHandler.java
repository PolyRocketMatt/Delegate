package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
import com.github.polyrocketmatt.delegate.core.command.tree.QueryResultNode;
import com.github.polyrocketmatt.delegate.core.exception.CommandExecutionException;

import java.util.Arrays;

public class CommandHandler implements Handler {

    private final CommandTree commandTree;

    public CommandHandler() {
        this.commandTree = new CommandTree();
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

        if (!executionNode.isVerified())
            throw new CommandExecutionException("Expected to execute a verified command, found an attributed command instead");

        //  We can then parse the remaining arguments, apply rules to them and parse them.
        String[] remainingArguments = queryResultNode.remainingArgs();
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) executionNode.getCommand();

        this.verifyArguments(command, remainingArguments);

        //  We can execute the command, which should be a verified command since it resides in a command node.
        //  Finally, we parse argument rules, resolve possible contexts and return proper results.

        return true;
    }

    private void verifyArguments(VerifiedDelegateCommand command, String[] arguments) {
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
