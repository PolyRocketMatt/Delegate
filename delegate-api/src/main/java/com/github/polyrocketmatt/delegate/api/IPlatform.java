package com.github.polyrocketmatt.delegate.api;

public interface IPlatform {

    String getPluginVersion();

    PlatformType getPlatformType();

    boolean hasPermission(String permission) throws UnsupportedOperationException;

}
