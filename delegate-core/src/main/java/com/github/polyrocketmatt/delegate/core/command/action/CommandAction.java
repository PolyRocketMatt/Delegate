package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

public abstract class CommandAction extends CommandAttribute implements Bufferable, RunnableCommandAction {

    private final int precedence;

    public CommandAction(String identifier, int precedence) {
        super(identifier);
        this.precedence = precedence;
    }

    public int getPrecedence() {
        return precedence;
    }
}
