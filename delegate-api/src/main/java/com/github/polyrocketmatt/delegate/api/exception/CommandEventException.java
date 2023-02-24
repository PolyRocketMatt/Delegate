// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public class CommandEventException extends RuntimeException {

    public CommandEventException(String message) {
        super(message);

        validate("message", String.class, message);
    }

}
