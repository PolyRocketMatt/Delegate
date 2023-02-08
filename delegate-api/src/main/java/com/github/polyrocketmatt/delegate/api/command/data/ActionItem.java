// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

/**
 * Represents a result produced by a {@link CommandAction}.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ActionItem<T> implements ActionResult {

    private final Result result;
    private final T item;

    /**
     * Creates a new {@link ActionItem} with the given {@link Result}.
     *
     * @param result The {@link Result} of the {@link CommandAction}.
     * @param item The item that was produced by the {@link CommandAction}.
     */
    public ActionItem(Result result, T item) {
        this.result = result;
        this.item = item;
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
     * Gets the item produced by the {@link CommandAction}.
     *
     * @return The item produced by the {@link CommandAction}.
     */
    public T getItem() {
        return item;
    }

    /**
     * Enum representing the result of a {@link CommandAction}.
     */
    public enum Result {
        SUCCESS,
        FAILURE;

        public boolean isSuccess() {
            return this == SUCCESS;
        }

        public boolean isFailure() {
            return this == FAILURE;
        }

    }

}
