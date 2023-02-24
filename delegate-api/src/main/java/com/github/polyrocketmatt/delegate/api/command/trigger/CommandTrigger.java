// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.trigger;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

public abstract class CommandTrigger extends CommandAttribute implements Bufferable {

    protected final BiConsumer<CommandDispatchInformation, CommandCapture> trigger;

    public CommandTrigger(@NotNull BiConsumer<CommandDispatchInformation, CommandCapture> trigger) {
        super(newId());
        validate("trigger", BiConsumer.class, trigger);

        this.trigger = trigger;
    }

    public void call(@NotNull CommandDispatchInformation information, @NotNull CommandCapture capture) {
        validate("information", CommandDispatchInformation.class, information);
        validate("capture", CommandCapture.class, capture);

        trigger.accept(information, capture);
    }

    public abstract boolean shouldTrigger(List<ActionItem.Result> results);

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.TRIGGER;
    }
}
