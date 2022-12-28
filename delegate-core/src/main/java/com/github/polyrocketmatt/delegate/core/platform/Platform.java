package com.github.polyrocketmatt.delegate.core.platform;

public interface Platform {

    String getPluginVersion();

    PlatformType getPlatformType();

    boolean hasPermission(String permission) throws UnsupportedOperationException;

}
