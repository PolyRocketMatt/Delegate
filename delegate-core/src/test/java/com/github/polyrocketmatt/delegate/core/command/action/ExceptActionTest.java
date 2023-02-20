// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptActionTest {

    @BeforeEach
    public void setup() {
        getDelegate().setVerbose(true);
    }

    private static class ExceptCommanderEntity implements CommanderEntity {

        @Override
        public boolean hasPermission(String permission) {
            return true;
        }

        @Override
        public boolean isOperator() {
            return true;
        }

        @Override
        public void sendMessage(String message) {
            System.out.println(message);
        }

    }

    @Test
    public void testConstructor() {
        ExceptAction action = new ExceptAction((commander, type, arguments) -> {});

        assertNotNull(action.getIdentifier());
        assertEquals(AttributeType.EXCEPT_ACTION, action.getType());
    }

    @Test
    public void testConstructorWithNulLAction() {
        assertThrows(IllegalArgumentException.class, () -> new ExceptAction(null));
    }

    @Test
    public void testExecuteSuccess() {
        ExceptCommanderEntity commander = new ExceptCommanderEntity();
        FeedbackType type = FeedbackType.UNAUTHORIZED;
        List<String> arguments = List.of("test");
        ExceptAction action = new ExceptAction((entity, feedback, args) -> System.out.println("Hello World!"));

        assertDoesNotThrow(() -> action.run(commander, type, arguments));
    }

    @Test
    public void testExecuteWithNullParams() {
        ExceptCommanderEntity commander = new ExceptCommanderEntity();
        FeedbackType type = FeedbackType.UNAUTHORIZED;
        List<String> arguments = List.of("test");
        ExceptAction action = new ExceptAction((entity, feedback, args) -> System.out.println("Hello World!"));

        assertThrows(IllegalArgumentException.class, () -> action.run(null, type, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(commander, null, arguments));
        assertThrows(IllegalArgumentException.class, () -> action.run(commander, type, null));
    }

    @Test
    public void testExecuteFailure() {
        ExceptCommanderEntity commander = new ExceptCommanderEntity();
        FeedbackType type = FeedbackType.UNAUTHORIZED;
        List<String> arguments = List.of("test");
        ExceptAction action = new ExceptAction((entity, feedback, args) -> {
            throw new IllegalStateException("This should not be thrown.");
        });

        assertDoesNotThrow(() -> action.run(commander, type, arguments));
    }

}
