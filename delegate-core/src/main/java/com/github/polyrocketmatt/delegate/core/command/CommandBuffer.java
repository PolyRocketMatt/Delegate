package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.Iterator;
import java.util.List;

public record CommandBuffer<T extends Bufferable>(List<T> bufferElements) implements Iterable<T> {

    public int size() {
        return bufferElements.size();
    }

    public T get(int index) {
        return bufferElements.get(index);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<T> iterator() {
        return bufferElements.iterator();
    }
}
