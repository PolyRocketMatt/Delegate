package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;

/**
 * Represents an argument of type {@link T} that has been processed by an {@link ArgumentParser}
 * and is ready to be used in a {@link CommandAction}.
 *
 * @param output The output of the argument.
 * @param <T> The type of the output.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record Argument<T>(T output) { }
