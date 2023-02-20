package com.github.polyrocketmatt.delegate.api.configuration;

import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DelegateConfigurationTest {

    @Test
    public void testConstructor() {
        DelegateConfiguration configuration = new DelegateConfiguration();

        for (FeedbackType type : FeedbackType.values())
            assertEquals(type.getDefaultMessage(), configuration.get(type));
    }

    @Test
    public void testSetFeedback() {
        DelegateConfiguration configuration = new DelegateConfiguration()
                .setFeedback(FeedbackType.UNAUTHORIZED, "Unauthorized");

        assertEquals("Unauthorized", configuration.get(FeedbackType.UNAUTHORIZED));
    }

}
