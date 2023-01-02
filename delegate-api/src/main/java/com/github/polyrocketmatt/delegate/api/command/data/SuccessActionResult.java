package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;

/**
 * Represents an {@link ActionItem} from a {@link CommandAction}
 * that was executed successfully.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class SuccessActionResult extends ActionItem<Object> {

    /**
     * Creates a new {@link SuccessActionResult}.
     */
    public SuccessActionResult() {
        super(Result.SUCCESS, null);
    }

}
