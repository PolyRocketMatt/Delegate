// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.event;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

@API(status = API.Status.STABLE, since = "0.0.1")
public interface CommandEvent {

    @NotNull CommandDispatchInformation getDispatchInformation();

    @NotNull CommandCapture getCapture();

}
