package com.github.polyrocketmatt.delegate.core.command;

import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.api.command.property.CommandProperty;
import com.github.polyrocketmatt.delegate.api.command.trigger.CommandTrigger;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.action.RunnableAction;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.trigger.FailureTrigger;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DelegateCommandBuilderTest {

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

    private static class NonExistentAttribute extends CommandAttribute {

        public NonExistentAttribute(@NotNull String identifier) {
            super(identifier);
        }

        @Override
        public @NotNull String getIdentifier() {
            return super.getIdentifier();
        }
    }

    private final DelegateCommandBuilder builder =  new TestCommandBuilder()
            .withDefinition(new NameDefinition("test"))
            .withDefinition(new DescriptionDefinition("Just a simple description"))
            .withAlias("alias1")
            .withAction(new RunnableAction(() -> System.out.println("Hello, World!")))
            .withInt("int", "int argument", 0)
            .withDouble("double", "double argument", 0.0)
            .withFloat("float", "float argument", 0.0f)
            .withString("string", "string argument", "default")
            .withSubcommand("subcommand", "Just a subcommand description")
            .withAsync()
            .withIgnoreNull()
            .withPermission("permission.primary")
            .withOperatorPermission()
            .with(new FailureTrigger(((information, captures) -> System.out.println("Failed!"))))
            .with(new ExceptAction((commander, feedback, actions) -> System.out.println("Exception!")));

    @BeforeEach
    public void setUp() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());
    }

    @Test
    public void testConstructor() {
        assertEquals(15, builder.size());
    }

    @Test
    public void testBuild() {
        VerifiedDelegateCommand command = (VerifiedDelegateCommand) builder.build();

        assertNotNull(command);
        assertEquals("test", command.getNameDefinition().getValue());
        assertEquals("Just a simple description", command.getDescriptionDefinition().getValue());
        assertEquals(1, command.getAliases().length);

        assertEquals(1, command.getActionBuffer().size());
        assertEquals(4, command.getArgumentBuffer().size());
        assertEquals(2, command.getPropertyBuffer().size());
        assertEquals(2, command.getPermissionBuffer().size());
        assertEquals(1, command.getTriggerBuffer().size());
        assertEquals(1, command.getExceptBuffer().size());
    }

    @Test
    public void testFilter() {
        assertEquals(1, builder.filter(CommandAction.class).size());
        assertEquals(4, builder.filter(CommandArgument.class).size());
        assertEquals(2, builder.filter(CommandProperty.class).size());
        assertEquals(2, builder.filter(PermissionTier.class).size());
        assertEquals(1, builder.filter(CommandTrigger.class).size());
        assertEquals(1, builder.filter(ExceptAction.class).size());
    }

    @Test
    public void testFilterNull() {
        assertThrows(IllegalArgumentException.class, () -> builder.filter((Class<CommandAttribute>) null));
    }

    @Test
    public void testFilterWithRule() {
        assertEquals(1, builder.filter((attr) -> attr instanceof CommandAction).size());
        assertEquals(4, builder.filter((attr) -> attr instanceof CommandArgument<?>).size());
        assertEquals(2, builder.filter((attr) -> attr instanceof CommandProperty).size());
        assertEquals(2, builder.filter((attr) -> attr instanceof PermissionTier).size());
        assertEquals(1, builder.filter((attr) -> attr instanceof CommandTrigger).size());
        assertEquals(1, builder.filter((attr) -> attr instanceof ExceptAction).size());
    }

    @Test
    public void testFilterWithRuleNull() {
        assertThrows(IllegalArgumentException.class, () -> builder.filter((Function<CommandAttribute, Boolean>) null));
    }

    @Test
    public void testGetActions() {
        assertEquals(1, builder.getActions().size());
    }

    @Test
    public void testGetArguments() {
        assertEquals(4, builder.getArguments().size());
    }

    @Test
    public void testGetProperties() {
        assertEquals(2, builder.getProperties().size());
    }

    @Test
    public void testGetPermissions() {
        assertEquals(2, builder.getPermissionTiers().size());
    }

    @Test
    public void testGetTriggers() {
        assertEquals(1, builder.getTriggers().size());
    }

    @Test
    public void testGetExceptActions() {
        assertEquals(1, builder.getExceptActions().size());
    }

    @Test
    public void testGetDefinition() {
        assertEquals(4, builder.getDefinitions().size());
    }

    @Test
    public void testFind() {
        assertNotNull(builder.find(CommandAction.class));
        assertNotNull(builder.find(CommandArgument.class));
        assertNotNull(builder.find(CommandProperty.class));
        assertNotNull(builder.find(PermissionTier.class));
        assertNotNull(builder.find(CommandTrigger.class));
        assertNotNull(builder.find(ExceptAction.class));

        assertNull(builder.find(NonExistentAttribute.class));
    }

}
