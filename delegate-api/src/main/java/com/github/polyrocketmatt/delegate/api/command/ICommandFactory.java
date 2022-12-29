package com.github.polyrocketmatt.delegate.api.command;

public interface ICommandFactory {

    ICommandBuilder create(String name, String description);

}
