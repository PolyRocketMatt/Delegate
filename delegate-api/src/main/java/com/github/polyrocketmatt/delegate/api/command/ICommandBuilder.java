package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.definition.CommandDefinition;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Builder that allows to build a command.
 *
 */
public interface ICommandBuilder {

    IDelegateCommand build();

    ICommandBuilder with(ICommandAttribute attribute);

    ICommandBuilder withAction(CommandAction action);

    ICommandBuilder withArgument(CommandArgument<?> argument);

    ICommandBuilder withFloat(String description);

    ICommandBuilder withFloat(String description, float defaultValue);

    ICommandBuilder withInt(String description);

    ICommandBuilder withInt(String description, int defaultValue);

    ICommandBuilder withString(String description);

    ICommandBuilder withString(String description, String defaultValue);

    ICommandBuilder withDefinition(CommandDefinition<?> definition);

    ICommandBuilder withSubcommand(CommandDefinition<?> subcommand);

    ICommandBuilder withProperty(CommandProperty property);

    ICommandBuilder withAsync();

    ICommandBuilder withBrigadier();

    ICommandBuilder withIgnoreNull();

}
