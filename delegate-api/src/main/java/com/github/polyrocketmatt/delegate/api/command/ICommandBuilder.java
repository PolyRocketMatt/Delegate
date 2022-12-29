package com.github.polyrocketmatt.delegate.api.command;

/**
 * Builder that allows to build a command.
 *
 */
public interface ICommandBuilder {

    IDelegateCommand build();

    ICommandBuilder with(ICommandAttribute attribute);

    ICommandBuilder withAction(ICommandAttribute action);

    ICommandBuilder withArgument(ICommandAttribute argument);

    ICommandBuilder withFloat(ICommandAttribute argument);

    ICommandBuilder withInt(ICommandAttribute argument);

    ICommandBuilder withString(ICommandAttribute argument);

    ICommandBuilder withDefinition(ICommandAttribute definition);

    ICommandBuilder withSubcommand(ICommandAttribute subcommand);

    ICommandBuilder withProperty(ICommandAttribute property);

    ICommandBuilder withAsync();

    ICommandBuilder withBrigadier();

    ICommandBuilder withIgnoreNull();

}
