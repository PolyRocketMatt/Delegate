package com.github.polyrocketmatt.delegate.core.command.data;

public class ActionItem<T> extends ActionResult {

    private final T item;

    public ActionItem(Result result, T item) {
        super(result);
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
