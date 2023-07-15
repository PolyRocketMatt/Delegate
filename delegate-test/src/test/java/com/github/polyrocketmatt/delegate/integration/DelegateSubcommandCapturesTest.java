package com.github.polyrocketmatt.delegate.integration;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import com.github.polyrocketmatt.delegate.core.CommandBuilderImpl;
import com.github.polyrocketmatt.delegate.core.CommanderEntityImpl;
import com.github.polyrocketmatt.delegate.core.PlatformImpl;
import com.github.polyrocketmatt.delegate.core.command.definition.DescriptionDefinition;
import com.github.polyrocketmatt.delegate.core.command.definition.NameDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class DelegateSubcommandCapturesTest {

    private static final PlatformImpl PLATFORM = new PlatformImpl();
    private static final CommanderEntityImpl ENTITY = new CommanderEntityImpl();

    @BeforeEach
    public void reset() {
        if (getDelegate().getPlatform() == null)
            getDelegate().setPlatform(PLATFORM);

        //  Clear command roots
        getDelegate().getCommandHandler().clearCommandCache();
    }

    @Test
    public void testSubcommandCaptures() {
        new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a top-level description"))
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("subA"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withSupplierAction("subActionA", () -> 10)
                )
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("subB"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withSupplierAction("subActionB", () -> "This is a result")
                )
                .build();
        PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "subA" }));
        CommandCapture captureA = PLATFORM.getCommandCapture();

        PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "subB" }));
        CommandCapture captureB = PLATFORM.getCommandCapture();

        assertNotNull(captureA.getResultOf("subActionA"));
        assertEquals(10, (int) captureA.getResultOf("subActionA"));

        assertNotNull(captureB.getResultOf("subActionB"));
        assertEquals("This is a result", captureB.getResultOf("subActionB"));
    }

    @Test
    public void testArgumentativeSubcommandCapture() {
        new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a top-level description"))
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("sub"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withInt("a", "")
                        .withInt("b", "")
                        .withFunctionAction("subAction", (sender, context) -> {
                            Integer a = context.find("a");
                            Integer b = context.find("b");

                            if (a != null && b != null)
                                return a + b;
                            else
                                return null;
                        })
                )
                .build();

        PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "sub", "10", "20" }));
        CommandCapture capture = PLATFORM.getCommandCapture();

        assertNotNull(capture.getResultOf("subAction"));
        assertEquals(30, (int) capture.getResultOf("subAction"));

        assertThrows(CommandExecutionException.class, () -> PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "sub", "30" })), "Wrong number of arguments. Expected 2, got 1.");
    }

    @Test
    public void testNestedSubcommandCapture() {
        new CommandBuilderImpl()
                .withDefinition(new NameDefinition("test"))
                .withDefinition(new DescriptionDefinition("This is a top-level description"))
                .withSubcommand(new CommandBuilderImpl()
                        .withDefinition(new NameDefinition("sub"))
                        .withDefinition(new DescriptionDefinition("This is a subcommand description"))
                        .withSubcommand(new CommandBuilderImpl()
                                .withDefinition(new NameDefinition("subA"))
                                .withDefinition(new DescriptionDefinition("This is a sub-sub description"))
                                .withSupplierAction("action", () -> 10)
                        )
                        .withSubcommand(new CommandBuilderImpl()
                                .withDefinition(new NameDefinition("subB"))
                                .withDefinition(new DescriptionDefinition("This is a sub-sub description"))
                                .withSupplierAction("action", () -> "This is a result")
                        )
                )
                .build();

        PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "sub", "subA" }));
        CommandCapture captureA = PLATFORM.getCommandCapture();

        PLATFORM.execute(new CommandDispatchInformation(ENTITY, "test", new String[] { "sub", "subB" }));
        CommandCapture captureB = PLATFORM.getCommandCapture();

        assertNotNull(captureA.getResultOf("action"));
        assertEquals(10, (int) captureA.getResultOf("action"));

        assertNotNull(captureB.getResultOf("action"));
        assertEquals("This is a result", captureB.getResultOf("action"));
    }

}
