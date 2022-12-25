package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.List;

public record CommandBuffer<T extends Bufferable>(List<T> bufferElements) {

    public int size() {
        return bufferElements.size();
    }

}
