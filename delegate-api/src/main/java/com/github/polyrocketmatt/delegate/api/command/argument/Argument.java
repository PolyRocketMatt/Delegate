package com.github.polyrocketmatt.delegate.api.command.argument;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

/**
 * Represents an argument of type {@link T} that has been processed by an {@link ArgumentParser}
 * and is ready to be used in a {@link CommandAction}.
 *
 * @param identifier The identifier of the argument.
 * @param output The output of the argument.
 * @param <T> The type of the output.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record Argument<T>(String identifier, T output) { }
