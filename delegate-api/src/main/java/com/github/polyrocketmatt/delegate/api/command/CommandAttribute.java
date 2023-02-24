// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a command attribute that can be used to describe or modify
 * a command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class CommandAttribute implements ICommandAttribute {

    private final String identifier;

    /**
     * Creates a new command attribute with the given identifier.
     *
     * @param identifier The identifier of the attribute.
     */
    public CommandAttribute(@NotNull String identifier) {
        validate("identifier", String.class, identifier);

        if (identifier.isEmpty() || identifier.isBlank())
            throw new IllegalArgumentException("Identifier cannot be empty or blank");
        this.identifier = identifier;
    }

    /**
     * Gets the identifier of the attribute.
     *
     * @return The identifier of the attribute.
     */
    public @NotNull String getIdentifier() {
        return identifier;
    }
}
