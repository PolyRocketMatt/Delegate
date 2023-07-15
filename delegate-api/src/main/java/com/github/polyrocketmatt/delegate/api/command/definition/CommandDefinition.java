// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.definition;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a command definition that describes the command
 * in some way.
 *
 * @param <T> The type of the definition.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class CommandDefinition<T> extends CommandAttribute {

    private final T value;

    /**
     * Creates a new command definition with an identifier and value.
     *
     * @param identifier The identifier of the definition.
     * @param value The value of the definition.
     * @throws IllegalArgumentException If the identifier is null, empty or blank.
     * @throws IllegalArgumentException If the value is null.
     */
    public CommandDefinition(@NotNull String identifier, @NotNull T value) {
        super(identifier);
        validate("value", Object.class, value);

        this.value = value;
    }

    /**
     * Gets the value of the definition.
     *
     * @return The value of the definition.
     */
    public @NotNull T getValue() {
        return value;
    }

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.DEFINITION;
    }

}
