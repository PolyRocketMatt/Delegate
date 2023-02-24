// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import org.apiguardian.api.API;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

@API(status = API.Status.STABLE, since = "0.0.1")
public class CommandEventException extends RuntimeException {

    public CommandEventException(String message) {
        super(message);

        validate("message", String.class, message);
    }

}
