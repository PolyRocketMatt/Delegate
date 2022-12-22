package com.github.polyrocketmatt.delegate.core.platform;

import java.io.File;

public interface Platform {

    File getFileStorage();

    String getPluginVersion();

    PlatformType getPlatformType();

}
