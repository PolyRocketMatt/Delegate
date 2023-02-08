// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.ICommandAttribute;

/**
 * Represents the type of {@link ICommandAttribute} attribute
 */
public enum AttributeType {

    ACTION,
    ARGUMENT,
    DEFINITION,
    PROPERTY,
    TRIGGER,
    PERMISSION,
    UNKNOWN

}
