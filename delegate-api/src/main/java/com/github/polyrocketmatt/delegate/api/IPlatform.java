// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

@API(status = API.Status.STABLE, since = "0.0.1")
public interface IPlatform {

    @NotNull PlatformType getPlatformType();

    @NotNull ICommandFactory getFactoryImplementation();

    @NotNull ICommandBuilder createCommand(@NotNull String name, @NotNull String description);

    void registerToPlatform(@NotNull IDelegateCommand name) throws CommandRegisterException;

    void registerToPlayers(@NotNull IDelegateCommand name) throws CommandRegisterException;

    boolean execute(@NotNull CommandDispatchInformation information) throws CommandExecutionException;

    boolean hasPermission(@NotNull CommanderEntity entity, @NotNull String permission) throws UnsupportedOperationException;

    boolean isOperator(@NotNull CommanderEntity entity) throws UnsupportedOperationException;

    boolean dispatch(@NotNull CommandDispatchInformation information, @NotNull CommandCapture capture);

    boolean metricsEnabled();

}
