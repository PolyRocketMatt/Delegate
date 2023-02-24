package com.github.polyrocketmatt.delegate.api.command.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SuccessActionResultTest {

    @Test
    public void testConstructor() {
        SuccessActionResult successActionResult = new SuccessActionResult();

        assertEquals(ActionItem.Result.SUCCESS, successActionResult.getResult());
        assertNull(successActionResult.getItem());
    }

}
