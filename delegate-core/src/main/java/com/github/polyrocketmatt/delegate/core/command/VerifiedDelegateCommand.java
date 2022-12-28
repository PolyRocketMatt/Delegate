package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;

/**
 * Implements the {@link DelegateCommand} interface and provides a class that stores
 * the name and description of the command together with the {@link CommandAction}s,
 * {@link CommandArgument}s and {@link CommandProperty}s that are used to store the
 * attributes of the command. The command is said to be verified since because it has
 * been constructed by {@link com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler}
 * and therefore was constructed properly.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class VerifiedDelegateCommand implements DelegateCommand {

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

    /**
     * Gets the {@link CommandBuffer} that stores the {@link CommandArgument}s of the command.
     *
     * @return The {@link CommandBuffer} that stores the {@link CommandArgument}s of the command.
     */
    public CommandBuffer<CommandArgument<?>> getArgumentBuffer() {
        return argumentBuffer;
    }

    /**
     * Gets the {@link CommandBuffer} that stores the {@link CommandProperty}s of the command.
     *
     * @return The {@link CommandBuffer} that stores the {@link CommandProperty}s of the command.
     */
    public CommandBuffer<CommandProperty> getPropertyBuffer() {
        return propertyBuffer;
    }

    /**
     * Gets the {@link CommandBuffer} that stores the {@link CommandAction}s of the command.
     *
     * @return The {@link CommandBuffer} that stores the {@link CommandAction}s of the command.
     */
    public CommandBuffer<CommandAction> getActionBuffer() {
        return actionBuffer;
    }

    /**
     * Creates a new {@link VerifiedCommandBuilder} that is used to create a new {@link VerifiedDelegateCommand}.
     *
     * @return A new {@link VerifiedCommandBuilder} that is used to create a new {@link VerifiedDelegateCommand}.
     */
    public static VerifiedCommandBuilder create() {
        return new VerifiedCommandBuilder();
    }

    /**
     * Builder class that is used to create a new {@link VerifiedDelegateCommand}.
     *
     * @since 0.0.1
     * @author Matthias Kovacic
     */
    public static class VerifiedCommandBuilder {

        private NameDefinition nameDefinition;
        private DescriptionDefinition descriptionDefinition;
        private CommandBuffer<CommandArgument<?>> argumentBuffer;
        private CommandBuffer<CommandProperty> propertyBuffer;
        private CommandBuffer<CommandAction> actionBuffer;

        /**
         * Builds a new {@link NameDefinition} and stores it in the builder.
         *
         * @param nameDefinition The name of the command.
         * @return The builder.
         */
        public VerifiedCommandBuilder buildNameDefinition(NameDefinition nameDefinition) {
            this.nameDefinition = nameDefinition;
            return this;
        }

        /**
         * Builds a new {@link DescriptionDefinition} and stores it in the builder.
         *
         * @param descriptionDefinition The description of the command.
         * @return The builder.
         */
        public VerifiedCommandBuilder buildDescriptionDefinition(DescriptionDefinition descriptionDefinition) {
            this.descriptionDefinition = descriptionDefinition;
            return this;
        }

        /**
         * Builds a new {@link CommandBuffer} that stores the {@link CommandArgument}s of the command.
         *
         * @param argumentBuffer The {@link CommandBuffer} that stores the {@link CommandArgument}s of the command.
         * @return The builder.
         */
        public VerifiedCommandBuilder buildArgumentBuffer(CommandBuffer<CommandArgument<?>> argumentBuffer) {
            this.argumentBuffer = argumentBuffer;
            return this;
        }

        /**
         * Builds a new {@link CommandBuffer} that stores the {@link CommandProperty}s of the command.
         *
         * @param propertyBuffer The {@link CommandBuffer} that stores the {@link CommandProperty}s of the command.
         * @return The builder.
         */
        public VerifiedCommandBuilder buildPropertyBuffer(CommandBuffer<CommandProperty> propertyBuffer) {
            this.propertyBuffer = propertyBuffer;
            return this;
        }

        /**
         * Builds a new {@link CommandBuffer} that stores the {@link CommandAction}s of the command.
         *
         * @param actionBuffer The {@link CommandBuffer} that stores the {@link CommandAction}s of the command.
         * @return The builder.
         */
        public VerifiedCommandBuilder buildActionBuffer(CommandBuffer<CommandAction> actionBuffer) {
            this.actionBuffer = actionBuffer;
            return this;
        }

        /**
         * Builds a new {@link VerifiedDelegateCommand} using the attributes that were stored in the builder.
         *
         * @return A new {@link VerifiedDelegateCommand} using the attributes that were stored in the builder.
         */
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
