package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

public class CommandAttributeChain {

    private final LinkedList<CommandAttribute> attributes;

    protected CommandAttributeChain() {
        this.attributes = new LinkedList<>();
    }

    public CommandAttributeChain append(CommandAttribute attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public DelegateCommand build() {
        return getDelegate().getAttributeHandler().process(new AttributedDelegateCommand(this));
    }

    public <T extends CommandAttribute> List<CommandAttribute> filter(Class<T> instance) {
        List<CommandAttribute> filtered = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (instance.isInstance(attribute))
                filtered.add(attribute);
        return filtered;
    }

    public <T> List<T> map(Function<CommandAttribute, T> function) {
        List<T> mapped = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            mapped.add(function.apply(attribute));
        return mapped;
    }

    public List<CommandDefinition<?>> getDefinitions() {
        List<CommandDefinition<?>> definitions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandDefinition<?>)
                definitions.add((CommandDefinition<?>) attribute);
        return definitions;
    }

    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandArgument<?>)
                arguments.add((CommandArgument<?>) attribute);
        return arguments;
    }

    public List<CommandProperty> getProperties() {
        List<CommandProperty> properties = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandProperty)
                properties.add((CommandProperty) attribute);
        return properties;
    }

    public int length() {
        return attributes.size();
    }

}
