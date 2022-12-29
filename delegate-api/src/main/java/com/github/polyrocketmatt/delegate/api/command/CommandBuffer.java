package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.Bufferable;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents a buffer that can be used to store {@link Bufferable} objects.
 *
 * @param bufferElements The elements to be stored in the buffer.
 * @param <T> The type of the elements to be stored in the buffer.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record CommandBuffer<T extends Bufferable>(List<T> bufferElements) implements Iterable<T> {

    /**
     * Gets the size of the buffer.
     *
     * @return The size of the buffer.
     */
    public int size() {
        return bufferElements.size();
    }

    /**
     * Gets the element at the specified index.
     *
     * @param index The index of the element to get.
     * @return The element at the specified index.
     */
    public T get(int index) {
        return bufferElements.get(index);
    }

    /**
     * Gets the elements in the buffer as an {@link Iterator}.
     *
     * @return The elements in the buffer as an {@link Iterator}.
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<T> iterator() {
        return bufferElements.iterator();
    }

    /**
     * Gets the elements in the buffer as a {@link Stream}.
     *
     * @return The elements in the buffer as a {@link Stream}.
     */
    public Stream<T> stream() {
        return bufferElements.stream();
    }

    /**
     * Find the index of the first element that matches the specified rule.
     *
     * @param rule The rule to match.
     * @return The index of the first element that matches the specified rule.
     */
    public int indexWhere(Function<T, Boolean> rule) {
        for (int i = 0; i < bufferElements.size(); i++) {
            T element = bufferElements.get(i);
            if (rule.apply(element) != null)
                return i;
        }

        return -1;
    }

}
