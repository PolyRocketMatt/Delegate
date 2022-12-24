package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.List;

public class CommandBuffer<T extends Bufferable> {

    private final List<T> bufferElements;

    public CommandBuffer(List<T> bufferElements) {
        this.bufferElements = bufferElements;
    }

    public List<T> getBufferElements() {
        return bufferElements;
    }
}
