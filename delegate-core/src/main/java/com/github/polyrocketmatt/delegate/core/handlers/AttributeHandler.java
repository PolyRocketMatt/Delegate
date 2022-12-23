package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandAttributeChain;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionAttribute;
import com.github.polyrocketmatt.delegate.core.command.definition.NameAttribute;
import com.github.polyrocketmatt.delegate.core.exception.AttributeException;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeHandler implements Handler {

    private final Map<NameAttribute, DescriptionAttribute> commandNames;

    public AttributeHandler() {
        this.commandNames = new HashMap<>();
    }

    public VerifiedDelegateCommand process(AttributedDelegateCommand command) {
        CommandAttributeChain chain = command.getAttributeChain();
        Tuple<NameAttribute, DescriptionAttribute> header = processHeader(command.getAttributeChain());

        this.checkUniqueName(header.getA());
        this.checkIdentifiers(chain);

        this.commandNames.put(header.getA(), header.getB());

        return new VerifiedDelegateCommand();
    }

    private Tuple<NameAttribute, DescriptionAttribute> processHeader(CommandAttributeChain chain) throws AttributeException {
        NameAttribute nameAttribute = getNameAttribute(chain);
        DescriptionAttribute descriptionAttribute = getDescriptionAttribute(chain);

        return new Tuple<>(nameAttribute, descriptionAttribute);
    }

    private NameAttribute getNameAttribute(CommandAttributeChain chain) {
        return chain.filter(NameAttribute.class).stream()
                .map(NameAttribute.class::cast)
                .findFirst()
                .orElseThrow(() -> new AttributeException("Attribute chain must contain a name attribute"));
    }

    private DescriptionAttribute getDescriptionAttribute(CommandAttributeChain chain) {
        return chain.filter(DescriptionAttribute.class).stream()
                .map(DescriptionAttribute.class::cast)
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

    public void checkUniqueName(NameAttribute nameAttribute) throws AttributeException {
        if (this.commandNames.keySet().stream().anyMatch(attribute -> attribute.getIdentifier().equals(nameAttribute.getIdentifier())))
            throw new AttributeException("Command name must be unique: %s".formatted(nameAttribute.getIdentifier()));
    }

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
        this.commandNames.clear();
    }
}
