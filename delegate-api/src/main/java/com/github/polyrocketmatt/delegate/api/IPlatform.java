package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

public interface IPlatform {

    PlatformType getPlatformType();

    boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException;

}
