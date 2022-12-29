package com.github.polyrocketmatt.delegate.core.command.properties;

import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;

/**
 * Represents a {@link CommandProperty} that allows the command to be integrated with the
 * Minecraft Brigadier system.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class BrigadierProperty extends CommandProperty {

    private static final String BRIGADIER_IDENTIFIER = "brigadier";

    /**
     * Creates a new {@link BrigadierProperty}.
     */
    public BrigadierProperty() { super(BRIGADIER_IDENTIFIER); }
}
