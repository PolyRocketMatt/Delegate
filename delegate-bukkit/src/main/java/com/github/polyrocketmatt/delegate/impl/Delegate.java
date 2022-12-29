package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.impl.command.BukkitCommandFactory;
import com.github.polyrocketmatt.delegate.impl.entity.BukkitPlayerCommander;

public class Delegate implements IPlatform {

    private static final BukkitCommandFactory factory = new BukkitCommandFactory();

    public static DelegateAPI getDelegateAPI() {
        return DelegateCore.getDelegateAPI();
    }

    public static BukkitCommandFactory getFactory() {
        return factory;
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUKKIT;
    }

    @Override
    public ICommandFactory getFactoryImplementation() {
        return factory;
    }

    @Override
    public boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException {
        if (!(entity instanceof BukkitPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.hasPermission(permission);
    }
}
