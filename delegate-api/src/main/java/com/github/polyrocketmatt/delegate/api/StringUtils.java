package com.github.polyrocketmatt.delegate.api;

import java.util.UUID;

public class StringUtils {

    public static String newId() {
        return UUID.randomUUID().toString();
    }

}