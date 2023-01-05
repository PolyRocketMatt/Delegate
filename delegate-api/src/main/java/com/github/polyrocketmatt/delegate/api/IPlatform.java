package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegistrationException;

public interface IPlatform {

    PlatformType getPlatformType();

    ICommandFactory getFactoryImplementation();

    void register(IDelegateCommand name) throws CommandRegistrationException;

    boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException;

    boolean dispatch(CommandDispatchInformation information, CommandCapture capture);

    boolean metricsEnabled();

}
