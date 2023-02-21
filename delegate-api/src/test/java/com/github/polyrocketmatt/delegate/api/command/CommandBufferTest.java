package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.Bufferable;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandBufferTest {

    private final List<BufferableInt> items = List.of(
            new BufferableInt(1),
            new BufferableInt(2),
            new BufferableInt(3)
    );

    private static record BufferableInt(int value) implements Bufferable {

        @Override
        public boolean equals(Object obj) {
            return obj instanceof BufferableInt && ((BufferableInt) obj).value == value;
        }
    }

    @Test
    public void testConstructor() {
        CommandBuffer<BufferableInt> buffer = new CommandBuffer<>(items);

        assertEquals(items, buffer.bufferElements());
        assertEquals( 3, buffer.size());
    }

    @Test
    public void testConstructorNullItems() {
        assertThrows(NullPointerException.class, () -> new CommandBuffer<>(null));
    }

    @Test
    public void testGet() {
        CommandBuffer<BufferableInt> buffer = new CommandBuffer<>(items);

        assertEquals(new BufferableInt(1), buffer.get(0));
        assertEquals( new BufferableInt(2), buffer.get(1));
        assertEquals( new BufferableInt(3), buffer.get(2));
    }

    @Test
    public void testIterator() {
        CommandBuffer<BufferableInt> buffer = new CommandBuffer<>(items);

        for (BufferableInt item : buffer)
            assertTrue(items.contains(item));
    }

    @Test
    public void testStream() {
        CommandBuffer<BufferableInt> buffer = new CommandBuffer<>(items);
        List<Integer> mappedBuffer = buffer.stream().map(BufferableInt::value).toList();

        assertEquals(1, mappedBuffer.get(0));
        assertEquals(2, mappedBuffer.get(1));
        assertEquals(3, mappedBuffer.get(2));
    }

    @Test
    public void testIndexWhere() {
        CommandBuffer<BufferableInt> buffer = new CommandBuffer<>(items);

        assertEquals(0, buffer.indexWhere(item -> item.value == 1));
        assertEquals(1, buffer.indexWhere(item -> item.value == 2));
        assertEquals(2, buffer.indexWhere(item -> item.value == 3));

        assertEquals(-1, buffer.indexWhere(item -> item.value == 4));
    }

}
