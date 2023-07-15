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
import com.github.polyrocketmatt.delegate.core.InvalidCommandNodeImpl;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.permission.PermissionTierType;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the registration of (in)valid and duplicate commands.
 */
public class DelegateCommandRegistrationTest {

    private static final IPlatform PLATFORM = new PlatformImpl();
    private static final CommanderEntityImpl ENTITY = new CommanderEntityImpl();
    private static CommandBuilderImpl BUILDER = new CommandBuilderImpl();



    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(PLATFORM);

        BUILDER = new CommandBuilderImpl();

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

    @Test
    public void testInvalidCommandNodeRegistration() {
        assertThrows(CommandRegisterException.class, () -> DelegateCore.getDelegate().registerCommand(null), "Node cannot be null");
        assertThrows(CommandRegisterException.class, () -> DelegateCore.getDelegate().registerCommand(new InvalidCommandNodeImpl()), "Node must be an instance of CommandNode");
    }

    @Test
    public void testValidCommandNodeRegistration() {
        DelegateCommand command = new AttributedDelegateCommand(BUILDER
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a test command"))
        );
        CommandNode node = new CommandNode(command);

        assertTrue(DelegateCore.getDelegate().registerCommand(node));
    }

    @Test
    public void testValidBuiltCommandNodeRegistration() {
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

    @Test
    public void testDuplicateCommandRegistration() {
        CommandBuilderImpl command = BUILDER.withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a test command"))
                .withAction(new CommandAction("a1", PermissionTierType.OPERATOR.getTier(), 0) {
                    @Override
                    public @NotNull ActionItem<?> run(@NotNull CommanderEntity commander, @NotNull List<Argument<?>> arguments) {
                        return new ActionItem<>(ActionItem.Result.SUCCESS, true);
                    }
                });

        assertDoesNotThrow(command::build);
        assertThrows(CommandRegisterException.class, command::build, "Cannot overwrite command node with the same name: test");
    }

}
