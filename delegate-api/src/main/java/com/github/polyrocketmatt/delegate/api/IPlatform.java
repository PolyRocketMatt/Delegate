package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

public interface IPlatform {

    PlatformType getPlatformType();

    ICommandFactory getFactoryImplementation();

    boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException;

    boolean dispatch(CommandDispatchInformation information, CommandCapture capture);

}
