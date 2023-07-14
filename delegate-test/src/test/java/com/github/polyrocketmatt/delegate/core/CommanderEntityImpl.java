package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.jetbrains.annotations.NotNull;

public class CommanderEntityImpl implements CommanderEntity {

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        System.out.println(message);
    }

    @Override
    public boolean isPlayer() {
        return false;
    }
}
