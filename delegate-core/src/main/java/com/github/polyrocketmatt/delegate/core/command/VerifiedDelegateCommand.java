package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;

public class VerifiedDelegateCommand implements DelegateCommand, Descripted {

    private final NameDefinition nameDefinition;
    private final DescriptionDefinition descriptionDefinition;
    private final CommandBuffer<CommandArgument<?>> argumentBuffer;
    private final CommandBuffer<CommandProperty> propertyBuffer;
    private final CommandBuffer<CommandAction> actionBuffer;

    protected VerifiedDelegateCommand(NameDefinition nameDefinition, DescriptionDefinition descriptionDefinition,
                                      CommandBuffer<CommandArgument<?>> argumentBuffer,
                                      CommandBuffer<CommandProperty> propertyBuffer,
                                      CommandBuffer<CommandAction> actionBuffer) {
        this.nameDefinition = nameDefinition;
        this.descriptionDefinition = descriptionDefinition;
        this.argumentBuffer = argumentBuffer;
        this.propertyBuffer = propertyBuffer;
        this.actionBuffer = actionBuffer;
    }

    @Override
    public NameDefinition getNameDefinition() {
        return nameDefinition;
    }

    @Override
    public DescriptionDefinition getDescriptionDefinition() {
        return descriptionDefinition;
    }

    public CommandBuffer<CommandArgument<?>> getArgumentBuffer() {
        return argumentBuffer;
    }

    public CommandBuffer<CommandProperty> getPropertyBuffer() {
        return propertyBuffer;
    }

    public CommandBuffer<CommandAction> getActionBuffer() {
        return actionBuffer;
    }

    public static CommandBuilder create() {
        return new CommandBuilder();
    }

    public static class CommandBuilder {

        private NameDefinition nameDefinition;
        private DescriptionDefinition descriptionDefinition;
        private CommandBuffer<CommandArgument<?>> argumentBuffer;
        private CommandBuffer<CommandProperty> propertyBuffer;
        private CommandBuffer<CommandAction> actionBuffer;

        public CommandBuilder buildNameDefinition(NameDefinition nameDefinition) {
            this.nameDefinition = nameDefinition;
            return this;
        }

        public CommandBuilder buildDescriptionDefinition(DescriptionDefinition descriptionDefinition) {
            this.descriptionDefinition = descriptionDefinition;
            return this;
        }

        public CommandBuilder buildArgumentBuffer(CommandBuffer<CommandArgument<?>> argumentBuffer) {
            this.argumentBuffer = argumentBuffer;
            return this;
        }

        public CommandBuilder buildPropertyBuffer(CommandBuffer<CommandProperty> propertyBuffer) {
            this.propertyBuffer = propertyBuffer;
            return this;
        }

        public CommandBuilder buildActionBuffer(CommandBuffer<CommandAction> actionBuffer) {
            this.actionBuffer = actionBuffer;
            return this;
        }

        public VerifiedDelegateCommand build() {
            return new VerifiedDelegateCommand(
                    this.nameDefinition,
                    this.descriptionDefinition,
                    this.argumentBuffer,
                    this.propertyBuffer,
                    this.actionBuffer
            );
        }

    }

}
