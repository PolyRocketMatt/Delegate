package com.github.polyrocketmatt.delegate.core.command.action.resolver;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionBuffer;

import java.util.List;

public class BufferResolveAction extends CommandResolveAction {

    public BufferResolveAction(String identifier) {
        super(identifier, -1, input -> {
            CommandBuffer<CommandArgument<?>> arguments = input.getA();
            List<String> values = input.getB();
            ActionBuffer resultingBuffer = new ActionBuffer();

            for (int i = 0; i < arguments.size(); i++)
                resultingBuffer.add(arguments.get(i).parse(values.get(i)));
            return resultingBuffer;
        });
    }

}
