package com.github.polyrocketmatt.delegate.api.exception;// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import com.github.polyrocketmatt.delegate.api.exception.CommandExecutionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandExecutionExceptionTest {

    @Test
    public void testConstructor() {
        CommandDispatchInformation dispatch = new CommandDispatchInformation(
                new ConsoleCommander(),
                "test",
                new String[] { "arg1", "arg2" }
        );

        try {
            throw new CommandExecutionException(dispatch, "test", FeedbackType.UNAUTHORIZED, new Object[] { "test" });
        } catch (CommandExecutionException ex) {
            assertEquals(dispatch.commander(), ex.getCommander());
            assertEquals(dispatch.command(), ex.getCommand());
            assertEquals(dispatch.arguments(), ex.getArguments());
            assertEquals(FeedbackType.UNAUTHORIZED, ex.getFeedbackType());
            assertEquals("You do not have permission to execute the command: test", ex.getFeedback());
        }
    }

}
