package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;

import java.util.List;

public class VerifiedDelegateCommand implements DelegateCommand, Descripted {

    private final NameDefinition nameDefinition;
    private final DescriptionDefinition descriptionDefinition;
    private final List<CommandArgument<?>> commandArguments;
    private final boolean brigadierCompatible;
    private final boolean ignoreNull;

    protected VerifiedDelegateCommand(NameDefinition nameDefinition, DescriptionDefinition descriptionDefinition,
                                      List<CommandArgument<?>> commandArguments, List<CommandProperty> commandProperties) {
        this.nameDefinition = nameDefinition;
        this.descriptionDefinition = descriptionDefinition;
        this.commandArguments = commandArguments;
        this.brigadierCompatible = commandProperties.stream().anyMatch(property -> property instanceof BrigadierProperty);
        this.ignoreNull = commandProperties.stream().anyMatch(property -> property instanceof IgnoreNullProperty);
    }

    public List<CommandArgument<?>> getCommandArguments() {
        return this.commandArguments;
    }

    @Override
    public NameDefinition getNameDefinition() {
        return nameDefinition;
    }

    @Override
    public DescriptionDefinition getDescriptionDefinition() {
        return descriptionDefinition;
    }

    public boolean isBrigadierCompatible() {
        return this.brigadierCompatible;
    }

    public boolean isIgnoreNull() {
        return this.ignoreNull;
    }

    public static CommandBuilder create() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {

        private NameDefinition nameDefinition;
        private DescriptionDefinition descriptionDefinition;
        private List<CommandArgument<?>> commandArguments;
        private List<CommandProperty> commandProperties;

        public CommandBuilder buildNameDefinition(NameDefinition nameDefinition) {
            this.nameDefinition = nameDefinition;
            return this;
        }

        public CommandBuilder buildDescriptionDefinition(DescriptionDefinition descriptionDefinition) {
            this.descriptionDefinition = descriptionDefinition;
            return this;
        }

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
                    this.nameDefinition,
                    this.descriptionDefinition,
                    this.commandArguments,
                    this.commandProperties
            );
        }

    }

}
