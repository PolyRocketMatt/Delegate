// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

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

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
