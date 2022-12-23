package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;

import java.util.List;

public class VerifiedDelegateCommand implements DelegateCommand {

    private final CommandPath path;

    private final List<CommandArgument<?>> commandArguments;
    private boolean brigadierCompatible;
    private boolean ignoreNull;

    protected VerifiedDelegateCommand(List<CommandArgument<?>> commandArguments, List<CommandProperty> commandProperties) {
        this.path = null;
        this.commandArguments = commandArguments;
        this.brigadierCompatible = commandProperties.stream().anyMatch(property -> property instanceof BrigadierProperty);
        this.ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);

        if (brigadierCompatible)
            setupBrigadier();
    }

    private void setupBrigadier() {

    }

    @Override
    public CommandPath getPath() {
        return path;
    }

    public boolean isBrigadierCompatible() {
        return brigadierCompatible;
    }

    public boolean isIgnoreNull() {
        return ignoreNull;
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
