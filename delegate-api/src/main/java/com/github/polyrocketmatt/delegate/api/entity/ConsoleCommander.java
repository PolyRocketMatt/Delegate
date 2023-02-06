package com.github.polyrocketmatt.delegate.api.entity;

/**
 * Represents a command issuer from the console.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConsoleCommander implements CommanderEntity {

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

}
