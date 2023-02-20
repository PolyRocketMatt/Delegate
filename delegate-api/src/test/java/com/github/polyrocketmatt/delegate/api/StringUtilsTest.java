package com.github.polyrocketmatt.delegate.api;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    private final Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Test
    public void testNewId() {
        String id = StringUtils.newId();

        assertTrue(UUID_REGEX.matcher(id).matches());
    }

}
