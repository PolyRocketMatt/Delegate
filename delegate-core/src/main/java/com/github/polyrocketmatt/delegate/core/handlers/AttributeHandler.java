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
        List<String> identifiers = chain.map(CommandAttribute::getIdentifier);

        if (chain.length() != identifiers.stream().distinct().count()) {
            List<String> duplicates = identifiers.stream()
                    .filter(i -> identifiers.indexOf(i) != identifiers.lastIndexOf(i))
                    .distinct()
                    .toList();

            throw new AttributeException("Attribute identifiers must be unique: %s".formatted(duplicates.get(0)));
        }
    }

    public void checkUniqueName(NameDefinition nameAttribute) throws AttributeException {
        if (this.commandMap.keySet().stream().anyMatch(path -> path.getName().getIdentifier().equals(nameAttribute.getIdentifier())))
            throw new AttributeException("Command name must be unique: %s".formatted(nameAttribute.getIdentifier()));
    }

    public List<CommandArgument<?>> processArguments(CommandAttributeChain chain) {
        return chain.getArguments();
    }

    public List<CommandProperty> processProperties(CommandAttributeChain chain) {
        return chain.getProperties();
    }

    public List<DelegateCommand> processSubCommands(CommandAttributeChain chain) {
        List<SubcommandDefinition> subCommandDefinitions = chain.getDefinitions()
                .stream()
                .filter(sc -> sc instanceof SubcommandDefinition)
                .map(SubcommandDefinition.class::cast)
                .toList();

        //  Verify all subcommands before returning them and continuing parsing the parent command!
        return subCommandDefinitions.stream()
                .map(subCommandDefinition -> {
                    DelegateCommand command = subCommandDefinition.getValue();

                    if (command instanceof VerifiedDelegateCommand)
                        return command;
                    else if (command instanceof AttributedDelegateCommand)
                        return this.process((AttributedDelegateCommand) command);
                    else
                        throw new AttributeException("Subcommand must be an attributed or verified command");
                })
                .toList();
    }

    @Override
    public void init() {}

    @Override
    public void destroy() {
        this.commandMap.clear();
    }
}
