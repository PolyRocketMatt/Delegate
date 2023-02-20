package com.github.polyrocketmatt.delegate.api.command;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ICommandAttributeTest {

    @Test
    public void testDefaultAttribute() {
        ICommandAttribute attribute = new ICommandAttribute() {};

        assertEquals(AttributeType.UNKNOWN, attribute.getType());
    }

}
