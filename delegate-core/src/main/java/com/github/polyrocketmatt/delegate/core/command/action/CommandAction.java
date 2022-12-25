package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;

import java.util.List;
import java.util.function.Function;

public class CommandAction extends CommandAttribute implements Bufferable {

    private final int precedence;
    private final Function<Tuple<CommandBuffer<CommandArgument<?>>, List<String>>, ActionResult> action;

    public CommandAction(String identifier, int precedence, Function<Tuple<CommandBuffer<CommandArgument<?>>, List<String>>, ActionResult> action) {
        super(identifier);
        this.precedence = precedence;
        this.action = action;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Function<Tuple<CommandBuffer<CommandArgument<?>>, List<String>>, ActionResult> getAction() {
        return action;
    }
}
