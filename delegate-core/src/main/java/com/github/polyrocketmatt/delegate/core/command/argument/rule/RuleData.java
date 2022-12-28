package com.github.polyrocketmatt.delegate.core.command.argument.rule;

/**
 * Represents data that can be passed from and to an {@link ArgumentRule}.
 *
 * @param value The value of the data.
 * @param <T> The type of the data.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record RuleData<T>(T value) { }