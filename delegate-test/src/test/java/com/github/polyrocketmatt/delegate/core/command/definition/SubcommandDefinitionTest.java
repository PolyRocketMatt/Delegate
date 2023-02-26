// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.definition;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandBuilder;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.TestCommandBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class SubcommandDefinitionTest {

    private final TestCommandBuilder builder = new TestCommandBuilder();

    private static class PlatformImpl implements IPlatform {

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

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    @Test
    public void testConstructorPrimary() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        SubcommandDefinition definition = new SubcommandDefinition(builder);

        assertNotNull(definition.getIdentifier());
        assertNotNull(definition.getValue());
        assertEquals(AttributeType.DEFINITION, definition.getType());
    }

    @Test
    public void testConstructorPrimaryWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new SubcommandDefinition(null));
    }

    @Test
    public void testConstructorSecondary() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        SubcommandDefinition definition = new SubcommandDefinition("test", "A simple test description");

        assertNotNull(definition.getIdentifier());
        assertNotNull(definition.getValue());
        assertEquals(AttributeType.DEFINITION, definition.getType());
    }

}
