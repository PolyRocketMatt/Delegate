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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DelegateRange<?> range)
            return range.getMin().equals(min) && range.getMax().equals(max);
        return false;
    }
}
