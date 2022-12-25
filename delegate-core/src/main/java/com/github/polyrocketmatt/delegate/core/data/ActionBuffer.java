package com.github.polyrocketmatt.delegate.core.data;

import java.util.ArrayList;
import java.util.List;

public class ActionBuffer implements ActionResult {

    private final List<ActionItem<?>> bufferElements;

    public ActionBuffer() {
        this.bufferElements = new ArrayList<>();
    }

    public List<ActionItem<?>> getBufferElements() {
        return bufferElements;
    }

    public void add(ActionItem<?> actionItem) {
        bufferElements.add(actionItem);
    }

}
