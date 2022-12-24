package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.function.Consumer;

public class CommandAction extends CommandAttribute implements Bufferable {

    private final Consumer<CommandBuffer<CommandArgument<?>>> action;

    public CommandAction(String identifier, Consumer<CommandBuffer<CommandArgument<?>>> action) {
        super(identifier);
        this.action = action;
    }

    public Consumer<CommandBuffer<CommandArgument<?>>> getAction() {
        return action;
    }
}
