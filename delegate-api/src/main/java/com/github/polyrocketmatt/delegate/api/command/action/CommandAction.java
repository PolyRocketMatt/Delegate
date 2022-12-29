package com.github.polyrocketmatt.delegate.api.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;

/**
 * Defines a command action with a precedence.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public abstract class CommandAction extends CommandAttribute implements Bufferable, RunnableCommandAction {

    private final int precedence;

    /**
     * Creates a new command action with an identifier and a precedence.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     */
    public CommandAction(String identifier, int precedence) {
        super(identifier);
        this.precedence = precedence;
    }

    /**
     * Gets the precedence of the command action.
     *
     * @return The precedence of the command action.
     */
    public int getPrecedence() {
        return precedence;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.ACTION;
    }
}
