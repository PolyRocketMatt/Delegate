package com.github.polyrocketmatt.delegate.core.command.action;

import org.junit.jupiter.api.Test;

public class CommandActionTest {

    @Test
    public void createSimpleAction() {
        CommandAction action = new CommandAction("test", (items) -> "Hello, World!");
    }

}
