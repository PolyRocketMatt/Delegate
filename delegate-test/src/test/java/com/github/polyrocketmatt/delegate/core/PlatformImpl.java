package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegateAPI;

public class PlatformImpl implements IPlatform {

    private CommandCapture commandCapture;

    public CommandCapture getCommandCapture() {
        return commandCapture;
    }

    public void flush() {
        commandCapture = null;
    }

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
        return new CommandBuilderImpl()
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
        return getDelegateAPI().getCommandHandler().handle(information);
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
        System.out.println("Dispatched command: " + information.command());

        this.commandCapture = capture;
        return true;
    }

    @Override
    public boolean metricsEnabled() {
        return false;
    }
}
