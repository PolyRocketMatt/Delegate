// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.tree;

import org.jetbrains.annotations.NotNull;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Represents the result of a query on a {@link CommandTree}.
 *
 * @param node The node that was found.
 * @param commandPattern The command pattern that was used to find the node.
 * @param remainingArgs The remaining arguments after the query.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record QueryResultNode(@NotNull CommandNode node, @NotNull String commandPattern,
                              @NotNull String[] remainingArgs) {

    public QueryResultNode {
        validate("node", CommandNode.class, node);
        validate("commandPattern", String.class, commandPattern);
        validate("remainingArgs", String[].class, remainingArgs);
        for (String arg : remainingArgs)
            validate("argument", String.class, arg);
    }

}
