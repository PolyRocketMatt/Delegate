// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents an {@link ActionItem} from a {@link CommandAction}
 * that produced an exception and failed.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FailureActionResult extends ActionItem<Exception> {

    /**
     * Creates a new {@link FailureActionResult} with exception that was thrown.
     *
     * @param ex The exception that was thrown by the {@link CommandAction}.
     */
    public FailureActionResult(@NotNull Exception ex) {
        super(Result.FAILURE, ex);

        validate("ex", Exception.class, ex);
    }

}
