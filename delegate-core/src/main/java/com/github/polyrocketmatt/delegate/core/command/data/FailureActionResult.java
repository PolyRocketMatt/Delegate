package com.github.polyrocketmatt.delegate.core.command.data;

import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;

/**
 * Represents an {@link ActionResult} from a {@link CommandAction}
 * that produced an exception and failed.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FailureActionResult extends ActionResult {

    private final Exception exception;

    /**
     * Creates a new {@link FailureActionResult} with exception that was thrown.
     *
     * @param ex The exception that was thrown by the {@link CommandAction}.
     */
    public FailureActionResult(Exception ex) {
        super(Result.FAILURE);
        this.exception = ex;
    }

    /**
     * Gets the exception that was thrown by the {@link CommandAction}.
     *
     * @return The exception that was thrown by the {@link CommandAction}.
     */
    public Exception getException() {
        return exception;
    }
}
