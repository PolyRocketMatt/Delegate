package com.github.polyrocketmatt.delegate.api.command.feedback;

import org.junit.jupiter.api.Test;

import java.util.MissingFormatArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandFeedbackInformationTest {

    @Test
    public void testConstructor() {
        CommandFeedbackInformation feedback = new CommandFeedbackInformation("test", FeedbackType.UNAUTHORIZED, new Object[] { "test" });

        assertEquals("test", feedback.feedback());
        assertEquals(FeedbackType.UNAUTHORIZED, feedback.type());
        assertEquals(1, feedback.args().length);
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new CommandFeedbackInformation(null, FeedbackType.UNAUTHORIZED, new Object[] { "test" }));
        assertThrows(IllegalArgumentException.class, () -> new CommandFeedbackInformation("test", null, new Object[] { "test" }));
        assertThrows(IllegalArgumentException.class, () -> new CommandFeedbackInformation("test", FeedbackType.UNAUTHORIZED, null));
        assertThrows(IllegalArgumentException.class, () -> new CommandFeedbackInformation("test", FeedbackType.UNAUTHORIZED, new Object[] { null }));
    }

    @Test
    public void testConstructFeedback() {
        CommandFeedbackInformation feedbackA = new CommandFeedbackInformation("test", FeedbackType.UNAUTHORIZED, new Object[] { "test" });
        assertEquals("You do not have permission to execute the command: test", feedbackA.getFormattedFeedback());

        CommandFeedbackInformation feedbackB = new CommandFeedbackInformation("test", FeedbackType.ARGS_INVALID_COUNT, new Object[] { 1 });
        assertThrows(MissingFormatArgumentException.class, feedbackB::getFormattedFeedback);
    }

}
