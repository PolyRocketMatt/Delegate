package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.entity.CommanderEntity;

public record CommandDispatchInformation(CommanderEntity commander, String command, String[] arguments) { }
