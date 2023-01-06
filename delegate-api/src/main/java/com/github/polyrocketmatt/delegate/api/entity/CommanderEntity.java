package com.github.polyrocketmatt.delegate.api.entity;

/**
 * Defines an entity that can issue commands.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface CommanderEntity {

    boolean hasPermission(String permission);

    boolean isOperator();

}
