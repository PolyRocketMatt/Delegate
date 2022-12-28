package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

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
     * @return A {@link VerifiedDelegateCommand} if the command is valid.
     * @throws AttributeException If the command is invalid.
     */
    public VerifiedDelegateCommand process(CommandNode parent, AttributedDelegateCommand command) {
        DelegateCommandBuilder chain = command.getAttributeChain();
        Tuple<NameDefinition, DescriptionDefinition> header = processHeader(command.getAttributeChain());

        this.checkUniqueName(parent, header.getA());
        this.checkIdentifiers(chain);

        CommandBuffer<CommandArgument<?>> argumentBuffer = new CommandBuffer<>(this.processArguments(chain));
        CommandBuffer<CommandProperty> propertyBuffer = new CommandBuffer<>(this.processProperties(chain));
        CommandBuffer<CommandAction> actionBuffer = new CommandBuffer<>(this.processActions(chain));

        VerifiedDelegateCommand verifiedCommand = VerifiedDelegateCommand.create()
                .buildNameDefinition(header.getA())
                .buildDescriptionDefinition(header.getB())
                .buildArgumentBuffer(argumentBuffer)
                .buildPropertyBuffer(propertyBuffer)
                .buildActionBuffer(actionBuffer)
                .build();

        //  If this is a super command, register the command and process the subcommands
        if (parent == null) {
            CommandNode rootNode = new CommandNode(verifiedCommand);

            //  Compute all sub-commands
            this.processSubCommands(rootNode, chain);

            //  Add tree to command handler
            ((CommandHandler) getDelegate().getCommandHandler()).registerTree(rootNode);
        } else {
            CommandNode childNode = new CommandNode(parent, verifiedCommand);

            parent.addChild(childNode);
        }

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

    private void checkUniqueName(CommandNode parent, NameDefinition definition) throws AttributeException {
        if (parent != null) {
            List<CommandNode> parentChildren = parent.getChildren();

            if (parentChildren.stream().anyMatch(node -> node.getCommand().getNameDefinition().getValue().equals(definition.getValue())))
                throw new AttributeException("Command name must be unique: %s".formatted(definition.getValue()));
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

    private void processSubCommands(CommandNode parent, DelegateCommandBuilder chain) {
        //  In the current chain, find all sub-command definitions
        List<DelegateCommandBuilder> subCommandChains = chain.filter(SubcommandDefinition.class)
                .stream()
                .map(SubcommandDefinition.class::cast)
                .map(SubcommandDefinition::getValue)
                .toList();
        List<AttributedDelegateCommand> attributedSubCommands = subCommandChains.stream()
                .map(AttributedDelegateCommand::new)
                .toList();

        //  Process all attributed sub-commands
        List<VerifiedDelegateCommand> processedSubCommands = attributedSubCommands.stream()
                .map(command -> this.process(parent, command))
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
            throw new AttributeException("Sub-command names must be unique: %s".formatted(duplicates.get(0)));
        }
    }

}
