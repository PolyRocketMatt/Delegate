package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;

import java.util.List;

public interface RunnableCommandAction {

    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs);

}
