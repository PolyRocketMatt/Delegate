// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.api.handlers.IHandler;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.definition.AliasDefinition;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * Handler that is responsible for processing and verifying the attributes of a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class AttributeHandler implements IHandler {

    /**
     * Creates a new {@link AttributeHandler} instance.
     */
    public AttributeHandler() {}

    /**
     * Processes an {@link AttributedDelegateCommand} and verifies the attributes. If the
     * command is valid, it will create and register a {@link VerifiedDelegateCommand} and
     * add it to the provided {@link CommandNode}.
     *
     * @param parent The parent {@link CommandNode} to add the command to.
     * @param command The {@link AttributedDelegateCommand} to process.
     * @param register Whether to register the command.
     * @return A {@link VerifiedDelegateCommand} if the command is valid.
     * @throws AttributeException If the command is invalid.
     */
    public VerifiedDelegateCommand process(@Nullable CommandNode parent, @NotNull AttributedDelegateCommand command, boolean register) throws CommandRegisterException {
        validate("command", AttributedDelegateCommand.class, command);

        DelegateCommandBuilder chain = command.getAttributeChain();
        Tuple<NameDefinition, DescriptionDefinition> header = processHeader(command.getAttributeChain());
        AliasDefinition[] aliases = processAliases(chain);

        this.checkUniqueName(parent, header.a().getValue());

        //  Check if all aliases are unique
        for (AliasDefinition alias : aliases)
            this.checkUniqueName(parent, alias.getValue());

        CommandBuffer<CommandArgument<?>> argumentBuffer = new CommandBuffer<>(this.processArguments(chain));
        CommandBuffer<CommandProperty> propertyBuffer = new CommandBuffer<>(this.processProperties(chain));
        CommandBuffer<CommandAction> actionBuffer = new CommandBuffer<>(this.processActions(chain));
        CommandBuffer<CommandTrigger> triggerBuffer = new CommandBuffer<>(this.processTriggers(chain));
        CommandBuffer<PermissionTier> permissionBuffer = new CommandBuffer<>(this.processPermissionTiers(chain));
        CommandBuffer<ExceptAction> exceptBuffer = new CommandBuffer<>(this.processExceptions(chain));

        //  Check identifiers after all attributes have been processed
        this.checkIdentifiers(chain);

        VerifiedDelegateCommand verifiedCommand = VerifiedDelegateCommand.create()
                .buildNameDefinition(header.a())
                .buildDescriptionDefinition(header.b())
                .buildAliasDefinitions(aliases)
                .buildArgumentBuffer(argumentBuffer)
                .buildPropertyBuffer(propertyBuffer)
                .buildActionBuffer(actionBuffer)
                .buildTriggerBuffer(triggerBuffer)
                .buildPermissionBuffer(permissionBuffer)
                .buildExceptBuffer(exceptBuffer)
                .build();

        //  If this is a super command, register the command and process the subcommands
        CommandNode rootNode = (parent == null) ? new CommandNode(verifiedCommand) : new CommandNode(parent, verifiedCommand);

        //  Parse the CURRENT command's subcommands -> Auto-register to the parent in constructor
        this.processSubCommands(rootNode, chain);

        //  Next, register the root node
        if (register)
            getDelegate().registerCommand(rootNode);

        //  Finally return the verified command
        return verifiedCommand;
    }

    private Tuple<NameDefinition, DescriptionDefinition> processHeader(DelegateCommandBuilder chain) throws AttributeException {
        NameDefinition nameAttribute = chain.filter(NameDefinition.class).stream()
                .map(NameDefinition.class::cast)
                .findFirst()
                .orElseThrow(() -> new AttributeException("Attribute chain must contain a name attribute"));
        DescriptionDefinition descriptionAttribute = chain.filter(DescriptionDefinition.class).stream()
                .map(DescriptionDefinition.class::cast)
                .findFirst()
                .orElseThrow(() -> new AttributeException("Attribute chain must contain a description attribute"));

        return new Tuple<>(nameAttribute, descriptionAttribute);
    }

    private AliasDefinition[] processAliases(DelegateCommandBuilder chain) {
        return chain.filter(AliasDefinition.class).stream()
                .map(AliasDefinition.class::cast)
                .toArray(AliasDefinition[]::new);
    }

    private void checkIdentifiers(DelegateCommandBuilder chain) throws AttributeException {
        List<CommandAttribute> filteredAttributes = chain.filter(attribute -> !(attribute instanceof SubcommandDefinition));
        List<String> identifiers = filteredAttributes.stream().map(CommandAttribute::getIdentifier).toList();
        if (filteredAttributes.size() != identifiers.stream().distinct().count()) {
            List<String> duplicates = identifiers.stream()
                    .filter(i -> identifiers.indexOf(i) != identifiers.lastIndexOf(i))
                    .distinct()
                    .toList();
            throw new AttributeException("Attribute identifiers must be unique: %s".formatted(duplicates.get(0)));
        }
    }

    private void checkUniqueName(CommandNode parent, String name) throws AttributeException {
        if (parent != null) {
            List<CommandNode> parentChildren = parent.getChildren();

            if (parentChildren.stream().anyMatch(node -> node.getCommand().getNameDefinition().getValue().equals(name)))
                throw new AttributeException("Command name must be unique: %s".formatted(name));
        }
    }

    private List<CommandArgument<?>> processArguments(DelegateCommandBuilder chain) {
        return chain.getArguments();
    }

    private List<CommandProperty> processProperties(DelegateCommandBuilder chain) {
        return chain.getProperties();
    }

    private List<CommandAction> processActions(DelegateCommandBuilder chain) {
        return chain.getActions();
    }

    private List<CommandTrigger> processTriggers(DelegateCommandBuilder chain) {
        return chain.getTriggers();
    }

    private List<PermissionTier> processPermissionTiers(DelegateCommandBuilder chain) {
        return chain.getPermissionTiers();
    }

    private List<ExceptAction> processExceptions(DelegateCommandBuilder chain) {
        return chain.getExceptActions();
    }

    private void processSubCommands(CommandNode parent, DelegateCommandBuilder chain) {
        //  In the current chain, find all sub-command definitions
        List<DelegateCommandBuilder> subCommandChains = chain.filter(SubcommandDefinition.class)
                .stream()
                .map(SubcommandDefinition.class::cast)
                .map(SubcommandDefinition::getValue)
                .toList();

        //  Early abort if there are no sub-commands
        if (subCommandChains.isEmpty())
            return;

        List<AttributedDelegateCommand> attributedSubCommands = subCommandChains.stream()
                .map(AttributedDelegateCommand::new)
                .toList();

        //  Process all attributed sub-commands
        List<VerifiedDelegateCommand> processedSubCommands = attributedSubCommands.stream()
                .map(command -> this.process(parent, command, false))
                .toList();

        //  Make sure all verified sub-commands have unique names
        List<String> subCommandNames = processedSubCommands.stream()
                .map(DelegateCommand::getNameDefinition)
                .map(NameDefinition::getValue)
                .toList();
        if (subCommandNames.size() != subCommandNames.stream().distinct().count()) {
            List<String> duplicates = subCommandNames.stream()
                    .filter(i -> subCommandNames.indexOf(i) != subCommandNames.lastIndexOf(i))
                    .distinct()
                    .toList();
            throw new AttributeException("Subcommand names must be unique: %s".formatted(duplicates.get(0)));
        }
    }

}
