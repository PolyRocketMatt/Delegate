// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents a result produced by a {@link CommandAction}.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public class ActionItem<T> implements ActionResult {

    private final Result result;
    private final T item;

    /**
     * Creates a new {@link ActionItem} with the given {@link Result}.
     *
     * @param result The {@link Result} of the {@link CommandAction}.
     * @param item The item that was produced by the {@link CommandAction}.
     */
    public ActionItem(@NotNull Result result, @Nullable T item) {
        validate("result", Result.class, result);

        this.result = result;
        this.item = item;
    }

    /**
     * Gets the result produced by the {@link CommandAction}.
     *
     * @return The result produced by the {@link CommandAction}.
     */
    public @NotNull Result getResult() {
        return result;
    }

    /**
     * Gets the item produced by the {@link CommandAction}.
     *
     * @return The item produced by the {@link CommandAction}.
     */
    public @Nullable T getItem() {
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
