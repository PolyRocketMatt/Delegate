package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandDispatchInformationTest {

    private final ConsoleCommander commander = new ConsoleCommander();
    private final String[] arguments = new String[] { "test" };

    @Test
    public void testConstructor() {
        CommandDispatchInformation information = new CommandDispatchInformation(commander, "test", arguments);

        assertEquals(information.commander(), commander);
        assertEquals(information.command(), "test");

        for (int i = 0; i < arguments.length; i++)
            assertEquals(information.arguments()[i], arguments[i]);
    }

    @Test
    public void testConstructorNullCommander() {
        assertThrows(IllegalArgumentException.class, () -> new CommandDispatchInformation(null, "test",
                new String[] {"test"}));
    }

    @Test
    public void testConstructorNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> new CommandDispatchInformation(new ConsoleCommander(), null,
                new String[] {"test"}));
    }

    @Test
    public void testConstructorNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> new CommandDispatchInformation(new ConsoleCommander(), "test",
                null));
    }

}
