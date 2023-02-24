// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.handlers;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import org.apiguardian.api.API;

@API(status = API.Status.STABLE, since = "0.0.1")
public abstract class CommandHandler implements IHandler {

    public abstract boolean handle(CommandDispatchInformation information) throws CommandExecutionException;

}
