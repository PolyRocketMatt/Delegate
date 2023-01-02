package com.github.polyrocketmatt.delegate.api.command.data;

import java.util.List;

public class CommandCapture {

    private final List<Capture> captures;

    public CommandCapture(List<Capture> captures) {
        this.captures = captures;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResultOf(String name) {
        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .map(Capture::result)
                .map(result -> (T) result.getItem())
                .orElse(null);
    }

    public Capture getCaptureOf(String name) {
        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .orElse(null);
    }

    public record Capture(String action, ActionItem<?> result) { }

}
