package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;
import com.github.polyrocketmatt.delegate.core.command.CommandPath;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeHandler implements Handler {

    private final Map<CommandPath, DescriptionDefinition> commandMap;

    public AttributeHandler() {
        this.commandMap = new HashMap<>();
    }

    public VerifiedDelegateCommand process(AttributedDelegateCommand command) {
        CommandAttributeChain chain = command.getAttributeChain();
        Tuple<NameDefinition, DescriptionDefinition> header = processHeader(command.getAttributeChain());

        this.checkUniqueName(header.getA());
        this.checkIdentifiers(chain);

        List<DelegateCommand> subCommands = this.processSubCommands(chain);
        List<CommandArgument<?>> arguments = this.processArguments(chain);
        List<CommandProperty> properties = this.processProperties(chain);

        return VerifiedDelegateCommand.create()
                .buildArguments(arguments)
                .buildProperties(properties)
                .build();
    }

    private Tuple<NameDefinition, DescriptionDefinition> processHeader(CommandAttributeChain chain) throws AttributeException {
        NameDefinition nameAttribute = getNameAttribute(chain);
        DescriptionDefinition descriptionAttribute = getDescriptionAttribute(chain);

        return new Tuple<>(nameAttribute, descriptionAttribute);
    }

    private NameDefinition getNameAttribute(CommandAttributeChain chain) {
        return chain.filter(NameDefinition.class).stream()
                .map(NameDefinition.class::cast)
                .findFirst()
                .orElseThrow(() -> new AttributeException("Attribute chain must contain a name attribute"));
    }

    private DescriptionDefinition getDescriptionAttribute(CommandAttributeChain chain) {
        return chain.filter(DescriptionDefinition.class).stream()
                .map(DescriptionDefinition.class::cast)
                .findFirst()
                .orElseThrow(() -> new AttributeException("Attribute chain must contain a description attribute"));
    }

    private void checkIdentifiers(CommandAttributeChain chain) throws AttributeException {
        List<String> identifiers = chain.map(CommandAttribute::getIdentifier)
                .stream()
                .filter(s -> !s.equals("subCommand"))
                .toList();
        if (chain.length() != identifiers.stream().distinct().count()) {
            List<String> duplicates = identifiers.stream()
                    .filter(i -> identifiers.indexOf(i) != identifiers.lastIndexOf(i))
                    .distinct()
                    .toList();

            throw new AttributeException("Attribute identifiers must be unique: %s".formatted(duplicates.get(0)));
        }
    }

    private void checkUniqueName(NameDefinition nameAttribute) throws AttributeException {
        if (this.commandMap.keySet().stream().anyMatch(path -> path.getName().getIdentifier().equals(nameAttribute.getIdentifier())))
            throw new AttributeException("Command name must be unique: %s".formatted(nameAttribute.getIdentifier()));
    }

    private List<CommandArgument<?>> processArguments(CommandAttributeChain chain) {
        return chain.getArguments();
    }

    private List<CommandProperty> processProperties(CommandAttributeChain chain) {
        return chain.getProperties();
    }

    private List<DelegateCommand> processSubCommands(CommandAttributeChain chain) {
        List<SubcommandDefinition> subCommandDefinitions = chain.getDefinitions()
                .stream()
                .filter(sc -> sc instanceof SubcommandDefinition)
                .map(SubcommandDefinition.class::cast)
                .toList();

        //  Check that all subcommands have a unique name definition
        List<DelegateCommand> subCommands = subCommandDefinitions.stream()
                .map(SubcommandDefinition::getValue)
                .toList();
        List<String> identifiers = subCommands.stream()
                .map(DelegateCommand::getPath)
                .map(CommandPath::getName)
                .map(NameDefinition::getIdentifier)
                .toList();

        if (identifiers.stream().distinct().count() != identifiers.size()) {
            List<String> duplicates = identifiers.stream()
                    .filter(i -> identifiers.indexOf(i) != identifiers.lastIndexOf(i))
                    .distinct()
                    .toList();
            throw new AttributeException("Attribute identifiers must be unique: %s".formatted(duplicates.get(0)));
        }

        //  Only process attributed commands
        List<AttributedDelegateCommand> attributedCommands = subCommands.stream()
                .filter(command -> command instanceof AttributedDelegateCommand)
                .map(AttributedDelegateCommand.class::cast)
                .toList();
        List<VerifiedDelegateCommand> verifiedCommands = subCommands.stream()
                .filter(command -> command instanceof VerifiedDelegateCommand)
                .map(VerifiedDelegateCommand.class::cast)
                .toList();

        if (attributedCommands.size() + verifiedCommands.size() != subCommands.size())
            throw new AttributeException("Subcommands must be attributed or verified");

        //  Verify all subcommands before returning them and continuing parsing the parent command!
        List<DelegateCommand> combinedSubcommands = new ArrayList<>(verifiedCommands);
        combinedSubcommands.addAll(attributedCommands.stream()
                .map(this::process)
                .toList());

        return combinedSubcommands;
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.commandMap.clear();
    }
}
