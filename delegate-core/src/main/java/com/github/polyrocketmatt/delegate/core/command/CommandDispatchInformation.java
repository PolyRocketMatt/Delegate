package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.entity.CommanderEntity;

import java.util.List;

public record CommandDispatchInformation(CommanderEntity commander, String command, List<String> arguments) { }
