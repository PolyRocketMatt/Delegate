package com.github.polyrocketmatt.delegate.api.command.trigger;

import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

public abstract class CommandTrigger extends CommandAttribute implements Bufferable {

    protected final BiConsumer<CommandDispatchInformation, CommandCapture> trigger;

    public CommandTrigger(BiConsumer<CommandDispatchInformation, CommandCapture> trigger) {
        super(newId());
        this.trigger = trigger;
    }

    public void call(CommandDispatchInformation information, CommandCapture capture) {
        trigger.accept(information, capture);
    }

    public abstract boolean shouldTrigger(List<ActionItem.Result> results);

}
