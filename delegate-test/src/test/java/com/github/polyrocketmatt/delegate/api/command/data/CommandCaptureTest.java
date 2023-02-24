// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandCaptureTest {

    private final List<CommandCapture.Capture> validCaptures = new ArrayList<>() {{
        add(new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, 1)));
        add(new CommandCapture.Capture("a3", new ActionItem<>(ActionItem.Result.SUCCESS, "result")));
        add(new CommandCapture.Capture("a4", new ActionItem<>(ActionItem.Result.FAILURE, null)));
    }};

    private final List<CommandCapture.Capture> invalidCaptures = new ArrayList<>() {{
        add(new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true)));
        add(new CommandCapture.Capture("a2", new ActionItem<>(ActionItem.Result.SUCCESS, 1)));
        add(new CommandCapture.Capture("a3", new ActionItem<>(ActionItem.Result.SUCCESS, "result")));
        add(null);
    }};

    @Test
    public void testConstructor() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertEquals(4, capture.size());
    }

    @Test
    public void testConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new CommandCapture(null));
        assertThrows(IllegalArgumentException.class, () -> new CommandCapture(invalidCaptures));
    }

    @Test
    public void testCaptureConstructor() {
        CommandCapture.Capture capture = new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, true));

        assertEquals("a1", capture.action());
        assertEquals(ActionItem.Result.SUCCESS, capture.result().getResult());
        assertEquals(true, capture.result().getItem());
    }

    @Test
    public void testCaptureConstructorIllegal() {
        assertThrows(IllegalArgumentException.class, () -> new CommandCapture.Capture(null, new ActionItem<>(ActionItem.Result.SUCCESS, true)));
        assertThrows(IllegalArgumentException.class, () -> new CommandCapture.Capture("a1", null));

        CommandCapture.Capture capture = new CommandCapture.Capture("a1", new ActionItem<>(ActionItem.Result.SUCCESS, null));

        assertEquals("a1", capture.action());
        assertEquals(ActionItem.Result.SUCCESS, capture.result().getResult());
        assertNull(capture.result().getItem());
    }

    @Test
    public void testIterator() {
        CommandCapture capture = new CommandCapture(validCaptures);

        int i = 0;
        for (CommandCapture.Capture c : capture)
            assertEquals(validCaptures.get(i++), c);
    }

    @Test
    public void testGetResults() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertEquals(4, capture.getResults().size());
        assertAll(
                () -> assertEquals(ActionItem.Result.SUCCESS, capture.getResults().get(0)),
                () -> assertEquals(ActionItem.Result.SUCCESS, capture.getResults().get(1)),
                () -> assertEquals(ActionItem.Result.SUCCESS, capture.getResults().get(2)),
                () -> assertEquals(ActionItem.Result.FAILURE, capture.getResults().get(3))
        );
    }

    @Test
    public void testGetResultOf() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertNotNull(capture.getResultOf("a1"));
        assertEquals(true, capture.getResultOf("a1").getItem());

        assertNotNull(capture.getResultOf("a2"));
        assertEquals(1, capture.getResultOf("a2").getItem());

        assertNotNull(capture.getResultOf("a3"));
        assertEquals("result", capture.getResultOf("a3").getItem());

        assertNotNull(capture.getResultOf("a4"));
        assertNull(capture.getResultOf("a4").getItem());
    }

    @Test
    public void testGetResultOfIllegal() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertThrows(IllegalArgumentException.class, () -> capture.getResultOf(null));
        assertNull(capture.getResultOf("a5"));
    }

    @Test
    public void testGetCaptureOf() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertNotNull(capture.getCaptureOf("a1"));
        assertEquals("a1", capture.getCaptureOf("a1").action());
        assertEquals(ActionItem.Result.SUCCESS, capture.getCaptureOf("a1").result().getResult());
        assertEquals(true, capture.getCaptureOf("a1").result().getItem());

        assertNotNull(capture.getCaptureOf("a2"));
        assertEquals("a2", capture.getCaptureOf("a2").action());
        assertEquals(ActionItem.Result.SUCCESS, capture.getCaptureOf("a2").result().getResult());
        assertEquals(1, capture.getCaptureOf("a2").result().getItem());

        assertNotNull(capture.getCaptureOf("a3"));
        assertEquals("a3", capture.getCaptureOf("a3").action());
        assertEquals(ActionItem.Result.SUCCESS, capture.getCaptureOf("a3").result().getResult());
        assertEquals("result", capture.getCaptureOf("a3").result().getItem());

        assertNotNull(capture.getCaptureOf("a4"));
        assertEquals("a4", capture.getCaptureOf("a4").action());
        assertEquals(ActionItem.Result.FAILURE, capture.getCaptureOf("a4").result().getResult());
        assertNull(capture.getCaptureOf("a4").result().getItem());
    }

    @Test
    public void testGetCaptureOfIllegal() {
        CommandCapture capture = new CommandCapture(validCaptures);

        assertThrows(IllegalArgumentException.class, () -> capture.getCaptureOf(null));
        assertNull(capture.getCaptureOf("a5"));
    }

}
