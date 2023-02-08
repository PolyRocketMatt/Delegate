package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;

public interface IPlatform {

    PlatformType getPlatformType();

    ICommandFactory getFactoryImplementation();

    void registerToPlatform(IDelegateCommand name) throws CommandRegisterException;

    boolean execute(CommandDispatchInformation information) throws CommandExecutionException;

    boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException;

    boolean isOperator(CommanderEntity entity) throws UnsupportedOperationException;

    boolean dispatch(CommandDispatchInformation information, CommandCapture capture);

    boolean metricsEnabled();

}
