// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;
import org.apiguardian.api.API;

/**
 * Represents the type of {@link ICommandAttribute} attribute
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public enum AttributeType {

    ACTION,
    EXCEPT_ACTION,
    ARGUMENT,
    DEFINITION,
    PROPERTY,
    TRIGGER,
    PERMISSION,
    UNKNOWN

}
