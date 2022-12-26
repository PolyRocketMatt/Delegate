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

        //  Check
        if (commandProperties.stream().noneMatch(property -> property instanceof IgnoreNullProperty) && commandArguments.size() != arguments.length)
            throw new CommandExecutionException("Expected " + commandArguments.size() + " arguments, found " + arguments.length);


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
