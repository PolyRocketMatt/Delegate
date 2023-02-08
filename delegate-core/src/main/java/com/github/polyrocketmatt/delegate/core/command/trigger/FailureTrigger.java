// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.trigger;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;

import java.util.List;
import java.util.function.BiConsumer;

public class FailureTrigger extends CommandTrigger {

    public FailureTrigger(BiConsumer<CommandDispatchInformation, CommandCapture> onFail) {
        super(onFail);
    }

    @Override
    public boolean shouldTrigger(List<ActionItem.Result> result) {
        return result.stream().allMatch(ActionItem.Result::isFailure);
    }

}
