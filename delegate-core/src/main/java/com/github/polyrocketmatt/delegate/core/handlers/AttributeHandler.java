package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandTree;
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
import java.util.Map;
import java.util.Optional;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

public class AttributeHandler implements Handler {

    public AttributeHandler() {}

    public VerifiedDelegateCommand process(CommandNode parent, AttributedDelegateCommand command) {
        CommandAttributeChain chain = command.getAttributeChain();
        Tuple<NameDefinition, DescriptionDefinition> header = processHeader(command.getAttributeChain());

        this.checkUniqueName(parent, header.getA());
        this.checkIdentifiers(chain);

        List<CommandArgument<?>> arguments = this.processArguments(chain);
        List<CommandProperty> properties = this.processProperties(chain);

        VerifiedDelegateCommand verifiedCommand = VerifiedDelegateCommand.create()
                .buildNameDefinition(header.getA())
                .buildDescriptionDefinition(header.getB())
                .buildArguments(arguments)
                .buildProperties(properties)
                .build();

        //  If this is a super command, add it to the map
        if (parent == null) {
            CommandNode rootNode = new CommandNode(verifiedCommand);
            CommandTree tree = new CommandTree(rootNode);

            //  Compute all sub-commands
            this.processSubCommands(rootNode, chain);

            //  Add tree to command handler
            getDelegate().getCommandHandler().registerTree(tree);
        } else {
            CommandNode childNode = new CommandNode(parent, verifiedCommand);

            parent.addChild(childNode);
        }

        return verifiedCommand;
    }

    private Tuple<NameDefinition, DescriptionDefinition> processHeader(CommandAttributeChain chain) throws AttributeException {
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

    private void checkIdentifiers(CommandAttributeChain chain) throws AttributeException {
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

    private List<CommandArgument<?>> processArguments(CommandAttributeChain chain) {
        return chain.getArguments();
    }

    private List<CommandProperty> processProperties(CommandAttributeChain chain) {
        return chain.getProperties();
    }

    private void processSubCommands(CommandNode parent, CommandAttributeChain chain) {
        //  In the current chain, find all sub-command definitions
        List<CommandAttributeChain> subCommandChains = chain.filter(SubcommandDefinition.class)
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

    @Override
    public void init() {}

    @Override
    public void destroy() {

    }
}
