package com.github.polyrocketmatt.delegate.core.command.action.resolver;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.utils.Tuple;

import java.util.List;
import java.util.function.Function;

public class CommandResolveAction extends CommandAction {

    public CommandResolveAction(String identifier, Function<Tuple<CommandBuffer<CommandArgument<?>>, List<String>>, ActionResult> action) {
        super(identifier, action);
    }

}
