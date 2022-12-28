package com.github.polyrocketmatt.delegate.api;

public interface ICommandBuilder {

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
