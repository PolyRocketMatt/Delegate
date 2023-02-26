// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.trigger;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class SuccessTrigger extends CommandTrigger {

    public SuccessTrigger(@NotNull BiConsumer<CommandDispatchInformation, CommandCapture> onSuccess) {
        super(onSuccess);
    }

    @Override
    public boolean shouldTrigger(@NotNull List<ActionItem.Result> result) {
        return result.stream().allMatch(ActionItem.Result::isSuccess);
    }
}
