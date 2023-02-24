// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandCaptureTest {

    private final List<CommandCapture.Capture> validCaptures = new ArrayList<>() {{
        add(new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, 1)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, "result")));
        add(new CommandCapture.Capture("a3", new ActionItem<>(ActionItem.Result.FAILURE, null)));
    }};

    private final List<CommandCapture.Capture> invalidCaptures = new ArrayList<>() {{
        add(new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, 1)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, "result")));
        add(null);
    }};

    @Test
    public void testConstructor() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertEquals(4, capture.size());
    }

    @Test
    public void testCaptureConstructor() {
        CommandCapture.Capture capture = new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true));

        assertEquals("a1", capture.action());
        assertEquals(ActionItem.Result.SUCCESS, capture.result().getResult());
        assertEquals(true, capture.result().getItem());
    }

}
