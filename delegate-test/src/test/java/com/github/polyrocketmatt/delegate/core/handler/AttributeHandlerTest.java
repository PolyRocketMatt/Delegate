package com.github.polyrocketmatt.delegate.core.handler;

import com.github.polyrocketmatt.delegate.api.exception.AttributeException;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.DelegateCommandImpl;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.AttributedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.command.VerifiedDelegateCommand;
import com.github.polyrocketmatt.delegate.core.command.action.ExceptAction;
import com.github.polyrocketmatt.delegate.core.command.action.RunnableAction;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.command.trigger.FailureTrigger;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeHandlerTest {

    private final AttributeHandler handler = new AttributeHandler();

    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

    private CommandBuilderImpl getBuilder() {
        return new CommandBuilderImpl()
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
    }

    @Test
    public void testProcess() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        CommandNode parent = new CommandNode(new DelegateCommandImpl("parent", "A parent command"));
        AttributedDelegateCommand command = new AttributedDelegateCommand(getBuilder());
        VerifiedDelegateCommand verifiedCommand = this.handler.process(parent, command, true);

        assertEquals(verifiedCommand, parent.getChildren().get(0).getCommand());
        assertEquals("test", verifiedCommand.getNameDefinition().getValue());
        assertEquals("Just a simple description", verifiedCommand.getDescriptionDefinition().getValue());
        assertEquals(1, command.getAliases().length);

        assertEquals(1, verifiedCommand.getActionBuffer().size());
        assertEquals(4, verifiedCommand.getArgumentBuffer().size());
        assertEquals(2, verifiedCommand.getPropertyBuffer().size());
        assertEquals(2, verifiedCommand.getPermissionBuffer().size());
        assertEquals(1, verifiedCommand.getTriggerBuffer().size());
        assertEquals(1, verifiedCommand.getExceptBuffer().size());
    }

    @Test
    public void testProcessNoNameDefinition() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand command = new AttributedDelegateCommand(new CommandBuilderImpl());


        assertThrows(AttributeException.class, () -> this.handler.process(null, command, true), "Attribute chain must contain a name attribute");
    }

    @Test
    public void testProcessNoDescriptionDefinition() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand command = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test")));

        assertThrows(AttributeException.class, () -> this.handler.process(null, command, true), "Attribute chain must contain a description attribute");
    }

    @Test
    public void testProcessDuplicateCommandName() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand parent = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("parent"))
                .withDefinition(new DescriptionDefinition("Just a simple description")));
        AttributedDelegateCommand commandA = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("Just a simple description")));
        AttributedDelegateCommand commandB = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("Just another simple description")));

        CommandNode parentNode = new CommandNode(this.handler.process(null, parent, true));
        this.handler.process(parentNode, commandA, true);
        assertThrows(AttributeException.class, () -> this.handler.process(parentNode, commandB, true), "Command name must be unique: %s".formatted("test"));
    }

    @Test
    public void testProcessDuplicateCommandNameRoot() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand commandA = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("Just a simple description")));
        AttributedDelegateCommand commandB = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("Just another simple description")));

        assertDoesNotThrow(() -> this.handler.process(null, commandA, true));
        assertThrows(CommandRegisterException.class, () -> this.handler.process(null, commandB, true), "Cannot overwrite command node with the same name: %s".formatted("test"));
    }

    @Test
    public void testProcessDuplicateIdentifiers() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand command = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("Just a simple description"))
                .withInt("int", "int argument", 0)
                .withInt("int", "int argument", 1));
        assertThrows(AttributeException.class, () -> this.handler.process(null, command, true), "Attribute identifiers must be unique: %s".formatted("int"));
    }

    @Test
    public void testProcessDuplicateSubCommands() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand command = new AttributedDelegateCommand(new CommandBuilderImpl()
                .withDefinition(new NameDefinition("parent"))
                .withDefinition(new DescriptionDefinition("Just a simple description"))
                .withSubcommand("test", "Just a simple description")
                .withSubcommand("test", "Just another simple description"));

        assertThrows(AttributeException.class, () -> this.handler.process(null, command, true), "Subcommand names must be unique: %s".formatted("test"));
    }

    @Test
    public void testProcessNull() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(new PlatformImpl());

        AttributedDelegateCommand command = new AttributedDelegateCommand(getBuilder());

        assertDoesNotThrow(() -> this.handler.process(null, command, true));
        assertThrows(IllegalArgumentException.class, () -> this.handler.process(null, null, true));
    }

}
