package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.action.FunctionAction;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.FloatArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.IntArgument;
import com.github.polyrocketmatt.delegate.core.command.argument.StringArgument;
import com.github.polyrocketmatt.delegate.core.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.command.properties.AsyncProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.BrigadierProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.CommandProperty;
import com.github.polyrocketmatt.delegate.core.command.properties.IgnoreNullProperty;
import com.github.polyrocketmatt.delegate.core.exception.AttributeException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

/**
 * Represents an extendable chain of attributes that can be applied to a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandAttributeChain {

    private final LinkedList<CommandAttribute> attributes;

    protected CommandAttributeChain() {
        this.attributes = new LinkedList<>();
    }

    /**
     * Append a new {@link CommandAttribute} to the chain.
     *
     * @param attribute The attribute to append.
     * @return The current chain.
     * @throws AttributeException If the attribute is a {@link CommandAction} whose precedence is less than 0.
     */
    public CommandAttributeChain append(CommandAttribute attribute) {
        if (attribute instanceof FunctionAction && ((FunctionAction) attribute).getPrecedence() < 0)
            throw new AttributeException("Action precedence must be greater than 0");

        this.attributes.add(attribute);
        return this;
    }

    /**
     * Append a new {@link CommandAction} to the chain.
     *
     * @param action The action to append.
     * @return The current chain.
     * @throws AttributeException If the action's precedence is less than 0.
     */
    public CommandAttributeChain withAction(CommandAction action) {
        //  Check that action precedence is greater than or equal to 0
        if (action.getPrecedence() <= 0)
            throw new AttributeException("Action precedence must be greater than 0");

        return this.append(action);
    }

    /**
     * Append a new {@link CommandArgument} to the chain.
     *
     * @param argument The argument to append.
     * @return The current chain.
     */
    public CommandAttributeChain withArgument(CommandArgument<?> argument) {
        return this.append(argument);
    }

    /**
     * Append a new {@link FloatArgument} to the chain.
     *
     * @param argument The argument to append.
     * @return The current chain.
     */
    public CommandAttributeChain withFloatArgument(FloatArgument argument) {
        return this.append(argument);
    }

    /**
     * Append a new {@link IntArgument} to the chain.
     *
     * @param argument The argument to append.
     * @return The current chain.
     */
    public CommandAttributeChain withIntArgument(IntArgument argument) {
        return this.append(argument);
    }

    /**
     * Append a new {@link StringArgument} to the chain.
     *
     * @param argument The argument to append.
     * @return The current chain.
     */
    public CommandAttributeChain withStringArgument(StringArgument argument) {
        return this.append(argument);
    }

    /**
     * Append a new {@link CommandDefinition} to the chain.
     *
     * @param definition The definition to append.
     * @return The current chain.
     */
    public CommandAttributeChain withDefinition(CommandDefinition<?> definition) {
        return this.append(definition);
    }

    /**
     * Append a new {@link SubcommandDefinition} to the chain.
     *
     * @param subcommand The subcommand to append.
     * @return The current chain.
     */
    public CommandAttributeChain withSubCommand(SubcommandDefinition subcommand) {
        return this.append(subcommand);
    }

    /**
     * Append a new {@link CommandProperty} to the chain.
     *
     * @param property The property to append.
     * @return The current chain.
     */
    public CommandAttributeChain withProperty(CommandProperty property) {
        return this.append(property);
    }

    /**
     * Append a new {@link AsyncProperty} to the chain.
     *
     * @return The current chain.
     */
    public CommandAttributeChain async() {
        return this.withProperty(new AsyncProperty());
    }

    /**
     * Append a new {@link BrigadierProperty} to the chain.
     *
     * @return The current chain.
     */
    public CommandAttributeChain withBrigadier() {
        return this.append(new BrigadierProperty());
    }

    /**
     * Append a new {@link IgnoreNullProperty} to the chain.
     *
     * @return The current chain.
     */
    public CommandAttributeChain withIgnoreNull() {
        return this.append(new IgnoreNullProperty());
    }

    /**
     * Uses the {@link com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler} to
     * process the attributes in the chain and constructs a new {@link DelegateCommand} instance.
     */
    public void build() {
        //  Add primary resolver to every command
        //  this.append(PRIMARY_RESOLVER);

        getDelegate().getAttributeHandler().process(null, new AttributedDelegateCommand(this));
    }

    /**
     * Filter the chain for attributes of the specified type.
     *
     * @param instance The type to filter for.
     * @return A list of attributes of the specified type.
     * @param <T> The type of attribute to filter for.
     */
    public <T extends CommandAttribute> List<CommandAttribute> filter(Class<T> instance) {
        List<CommandAttribute> filtered = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (instance.isInstance(attribute))
                filtered.add(attribute);
        return filtered;
    }

    /**
     * Filter the chain for attributes that fulfill the specified rule.
     *
     * @param rule The rule to filter for.
     * @return A list of attributes that fulfill the specified rule.
     */
    public List<CommandAttribute> filter(Function<CommandAttribute, Boolean> rule) {
        List<CommandAttribute> filtered = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (rule.apply(attribute))
                filtered.add(attribute);
        return filtered;
    }

    /**
     * Gets all {@link CommandAction}s in the chain.
     *
     * @return A list of all actions in the chain.
     */
    public List<CommandAction> getActions() {
        List<CommandAction> actions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandAction)
                actions.add((CommandAction) attribute);
        return actions;
    }

    /**
     * Gets all {@link CommandArgument}s in the chain.
     *
     * @return A list of all arguments in the chain.
     */
    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandArgument<?>)
                arguments.add((CommandArgument<?>) attribute);
        return arguments;
    }

    /**
     * Gets all {@link CommandDefinition}s in the chain.
     *
     * @return A list of all definitions in the chain.
     */
    public List<CommandDefinition<?>> getDefinitions() {
        List<CommandDefinition<?>> definitions = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandDefinition<?>)
                definitions.add((CommandDefinition<?>) attribute);
        return definitions;
    }

    /**
     * Gets all {@link CommandProperty}s in the chain.
     *
     * @return A list of all properties in the chain.
     */
    public List<CommandProperty> getProperties() {
        List<CommandProperty> properties = new LinkedList<>();
        for (CommandAttribute attribute : attributes)
            if (attribute instanceof CommandProperty)
                properties.add((CommandProperty) attribute);
        return properties;
    }

    /**
     * Find the first {@link CommandAttribute} in the chain that is an instance of the specified type.
     *
     * @param instance The type to find.
     * @return The first attribute of the specified type.
     * @param <T> The type of attribute to find.
     */
    public <T extends CommandAttribute> CommandAttribute find(Class<T> instance) {
        for (CommandAttribute attribute : attributes)
            if (instance.isInstance(attribute))
                return attribute;
        return null;
    }

    /**
     * Gets the size of the chain.
     *
     * @return The size of the chain.
     */
    public int size() {
        return attributes.size();
    }

}
