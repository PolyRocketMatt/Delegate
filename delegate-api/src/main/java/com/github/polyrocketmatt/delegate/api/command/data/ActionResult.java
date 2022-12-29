package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

/**
 * Represents a result produced by a {@link CommandAction}.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class ActionResult {

    private final Result result;

    /**
     * Creates a new {@link ActionResult} with the given {@link Result}.
     *
     * @param result The {@link Result} of the {@link CommandAction}.
     */
    public ActionResult(Result result) {
        this.result = result;
    }

    /**
     * Gets the result produced by the {@link CommandAction}.
     *
     * @return The result produced by the {@link CommandAction}.
     */
    public Result getResult() {
        return result;
    }

    /**
     * Enum representing the result of a {@link CommandAction}.
     */
    public enum Result {
        SUCCESS,
        FAILURE
    }

}
