package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

/**
 * Represents an {@link ActionResult} from a {@link CommandAction}
 * that produced an object of type {@link T}.
 *
 * @param <T> The type of object produced by the {@link CommandAction}.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ActionItem<T> extends ActionResult {

    private final T item;

    /**
     * Creates a new {@link ActionItem} with the given {@link Result} and item.
     *
     * @param result The {@link Result} of the {@link CommandAction}.
     * @param item The item produced by the {@link CommandAction}.
     */
    public ActionItem(Result result, T item) {
        super(result);
        this.item = item;
    }

    /**
     * Gets the item produced by the {@link CommandAction}.
     *
     * @return The item produced by the {@link CommandAction}.
     */
    public T getItem() {
        return item;
    }
}
