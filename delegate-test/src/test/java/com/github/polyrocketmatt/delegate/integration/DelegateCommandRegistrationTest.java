package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.CommanderEntityImpl;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DelegateCommandRegistrationTest {

    private static IPlatform PLATFORM;
    private static CommandBuilderImpl BUILDER;
    private static CommanderEntityImpl ENTITY;

    @BeforeAll
    public static void setup() {
        PLATFORM = new PlatformImpl();
        BUILDER = new CommandBuilderImpl();
        ENTITY = new CommanderEntityImpl();

        DelegateCore.getDelegate().setPlatform(PLATFORM);
    }

    @Test
    public void testIllegalCommandNodeRegistration() {
        assertThrows(CommandRegisterException.class, () -> { DelegateCore.getDelegate().registerCommand(null); }, "Node cannot be null");
    }

    @Test
    public void testNormalCommandNodeRegistration() {
        final int[] a = { 0 };

        DelegateCommand command = BUILDER.withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a test command"))
                .withAction(new CommandAction("a1", PermissionTierType.OPERATOR.getTier(), 0) {
                    @Override
                    public @NotNull ActionItem<?> run(@NotNull CommanderEntity commander, @NotNull List<Argument<?>> arguments) {
                        a[0] = 1;

                        return new ActionItem<>(ActionItem.Result.SUCCESS, true);
                    }
                })
                .build();
        CommandDispatchInformation information = new CommandDispatchInformation(ENTITY, command.getNameDefinition().getValue(), new String[0]);

        PLATFORM.execute(information);
        assertEquals(1, a[0]);
    }

}
