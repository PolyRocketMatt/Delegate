package com.github.polyrocketmatt.delegate.api.command.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FailureActionResultTest {

    private final Exception exception = new Exception("This is a test exception");

    @Test
    public void testConstructorPrimary() {
        FailureActionResult failureActionResult = new FailureActionResult(exception);

        assertEquals(ActionItem.Result.FAILURE, failureActionResult.getResult());
        assertEquals(exception, failureActionResult.getItem());
    }

    @Test
    public void testConstructorSecondary() {
        FailureActionResult failureActionResult = new FailureActionResult();

        assertEquals(ActionItem.Result.FAILURE, failureActionResult.getResult());
        assertNull(failureActionResult.getItem());
    }

}
