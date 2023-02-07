package com.github.polyrocketmatt.delegate.core.command.tree;

/**
 * Represents the result of a query on a {@link CommandTree}.
 *
 * @param node The node that was found.
 * @param remainingArgs The remaining arguments after the query.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record QueryResultNode(CommandNode node, String commandPattern, String[] remainingArgs) {}
