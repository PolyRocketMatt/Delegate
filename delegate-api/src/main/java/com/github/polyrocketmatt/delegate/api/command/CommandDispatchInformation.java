package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

/**
 * Record that contains information about a command that has been dispatched
 * on any platform.
 *
 * @param commander The {@link CommanderEntity} that dispatched the command.
 * @param command The command that was dispatched.
 * @param arguments The arguments that were passed to the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic 
 */
public record CommandDispatchInformation(CommanderEntity commander, String command, String[] arguments) { }
