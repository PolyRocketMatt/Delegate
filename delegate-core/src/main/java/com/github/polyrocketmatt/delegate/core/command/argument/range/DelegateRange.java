package com.github.polyrocketmatt.delegate.core.command.argument.range;

public abstract class DelegateRange<T> {

    private final T min;
    private final T max;

    public DelegateRange(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public abstract boolean contains(T value);

}
