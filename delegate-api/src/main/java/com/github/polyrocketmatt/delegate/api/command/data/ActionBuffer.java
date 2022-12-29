package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an {@link ActionResult} from a {@link CommandAction}
 * that produced a list of {@link ActionItem}s.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ActionBuffer extends ActionResult {

    private final List<ActionItem<?>> bufferElements;

    /**
     * Creates a new {@link ActionBuffer} with the given {@link Result} and buffer elements.
     *
     * @param result The {@link Result} of the {@link CommandAction}.
     * @param bufferElements The buffer elements produced by the {@link CommandAction}.
     */
    public ActionBuffer(Result result, List<ActionItem<?>> bufferElements) {
        super(result);
        this.bufferElements = new ArrayList<>();
    }

    /**
     * Gets the buffer elements produced by the {@link CommandAction}.
     *
     * @return The buffer elements produced by the {@link CommandAction}.
     */
    public List<ActionItem<?>> getBufferElements() {
        return bufferElements;
    }

}
