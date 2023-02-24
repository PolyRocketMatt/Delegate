// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents an {@link ActionItem} from a {@link CommandAction}
 * that produced an exception and failed.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public class FailureActionResult extends ActionItem<Exception> {

    /**
     * Creates a new {@link FailureActionResult} with exception that was thrown.
     *
     * @param ex The exception that was thrown by the {@link CommandAction}.
     */
    public FailureActionResult(@Nullable Exception ex) {
        super(Result.FAILURE, ex);
    }

    /**
     * Creates a new {@link FailureActionResult}.
     */
    public FailureActionResult() {
        this(null);
    }

}
