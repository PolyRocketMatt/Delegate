package com.github.polyrocketmatt.delegate.api.command;

public interface ICommandFactory {

    /**
     * Creates a new {@link ICommandFactory} with the given name and description.
     *
     * @param name The name of the command.
     * @param description The description of the command.
     * @return The new {@link ICommandFactory}.
     */
    ICommandBuilder create(String name, String description);

    //ICommandBuilder create(String name, String description);

}
