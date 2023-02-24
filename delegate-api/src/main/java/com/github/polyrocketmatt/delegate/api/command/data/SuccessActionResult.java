// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import org.apiguardian.api.API;

/**
 * Represents an {@link ActionItem} from a {@link CommandAction}
 * that was executed successfully.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
public class SuccessActionResult extends ActionItem<Object> {

    /**
     * Creates a new {@link SuccessActionResult}.
     */
    public SuccessActionResult() {
        super(Result.SUCCESS, null);
    }

}
