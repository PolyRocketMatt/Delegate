package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.data.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.min;

public class DefaultAction extends CommandAction {

    private final Function<List<Argument<?>>, ?> action;

    public DefaultAction(String identifier, int precedence, Function<List<Argument<?>>, ?> action) {
        super(identifier, precedence);
        this.action = action;
    }

    public DefaultAction(String identifier, Function<List<Argument<?>>, ?> action) {
        super(identifier, 0);
        this.action = action;
    }

    @Override
    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        int maxIndex = min(inputs.size(), arguments.size());

        List<Argument<?>> inputItems = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++)
            inputItems.add(arguments.get(i).parse(inputs.get(i)));
        return new ActionItem<>(action.apply(inputItems));
    }

}
