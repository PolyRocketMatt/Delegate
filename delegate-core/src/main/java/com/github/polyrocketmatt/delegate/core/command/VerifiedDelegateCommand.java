package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;

import java.util.List;

public class VerifiedDelegateCommand implements DelegateCommand {

    private final List<CommandArgument<?>> commandArguments;
    private final List<CommandProperty> commandProperties;

    protected VerifiedDelegateCommand(List<CommandArgument<?>> commandArguments, List<CommandProperty> commandProperties) {
        this.commandArguments = commandArguments;
        this.commandProperties = commandProperties;
    }

    public static CommandBuilder create() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {

        private List<CommandArgument<?>> commandArguments;
        private List<CommandProperty> commandProperties;

        public CommandBuilder buildArguments(List<CommandArgument<?>> commandArguments) {
            this.commandArguments = commandArguments;
            return this;
        }

        public CommandBuilder buildProperties(List<CommandProperty> commandProperties) {
            this.commandProperties = commandProperties;
            return this;
        }

        public VerifiedDelegateCommand build() {
            return new VerifiedDelegateCommand(
                    this.commandArguments,
                    this.commandProperties
            );
        }

    }

}
