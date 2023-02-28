package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.TestCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

public class PlatformImpl implements IPlatform {

    @Override
    public @NotNull PlatformType getPlatformType() {
        return null;
    }

    @Override
    public @NotNull ICommandFactory getFactoryImplementation() {
        return null;
    }

    @Override
    public @NotNull DelegateCommandBuilder createCommand(@NotNull String name, @NotNull String description) {
        return new TestCommandBuilder()
                .withDefinition(new NameDefinition(name))
                .withDefinition(new DescriptionDefinition(description));
    }

    @Override
    public void registerToPlatform(@NotNull IDelegateCommand name) throws CommandRegisterException {

    }

    @Override
    public void registerToPlayers(@NotNull IDelegateCommand name) throws CommandRegisterException {

    }

    @Override
    public boolean execute(@NotNull CommandDispatchInformation information) throws CommandExecutionException {
        return false;
    }

    @Override
    public boolean hasPermission(@NotNull CommanderEntity entity, @NotNull String permission) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean isOperator(@NotNull CommanderEntity entity) throws UnsupportedOperationException {
        return false;
    }

    @Override
    public boolean dispatch(@NotNull CommandDispatchInformation information, @NotNull CommandCapture capture) {
        return false;
    }

    @Override
    public boolean metricsEnabled() {
        return false;
    }
}
