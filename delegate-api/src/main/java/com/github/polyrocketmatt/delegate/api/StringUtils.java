// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import java.util.UUID;

public class StringUtils {

    public static String newId() {
        return UUID.randomUUID().toString();
    }

}
