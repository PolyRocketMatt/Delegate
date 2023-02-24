// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import org.apiguardian.api.API;

import java.util.UUID;

@API(status = API.Status.STABLE, since = "0.0.1")
public class StringUtils {

    public static String newId() {
        return UUID.randomUUID().toString();
    }

}
