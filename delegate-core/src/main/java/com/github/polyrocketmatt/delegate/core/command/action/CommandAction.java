package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.data.ActionResult;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.min;

public class CommandAction extends CommandAttribute implements Bufferable {

    private final int precedence;
    private final Function<List<ActionItem<?>>, ?> action;

    public CommandAction(String identifier, int precedence, Function<List<ActionItem<?>>, ?> action) {
        super(identifier);
        this.precedence = precedence;
        this.action = action;
    }

    public CommandAction(String identifier, Function<List<ActionItem<?>>, ?> action) {
        super(identifier);
        this.precedence = 0;
        this.action = action;
    }

    public int getPrecedence() {
        return precedence;
    }

    public ActionResult run(CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        int maxIndex = min(inputs.size(), arguments.size());

        List<ActionItem<?>> inputItems = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++)
            inputItems.add(arguments.get(i).parse(inputs.get(i)));

        return new ActionItem<>(action.apply(inputItems));
    }

}
