package com.github.polyrocketmatt.delegate.api;

public interface ICommandFactory {

    ICommandBuilder create(String name, String description);

}
