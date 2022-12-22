package com.github.polyrocketmatt.delegate.core.io;

import java.io.File;

public class FileHandler {

    public static final String PLUGIN_CONFIG_PATH = "configurations/";

    public static File getFile(File parent, String path) {
        return new File(parent, path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File create(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return file;
    }

    public static boolean exists(File file) {
        return file.exists();
    }

}
