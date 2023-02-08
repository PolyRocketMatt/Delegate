// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CommandCapture implements Iterable<CommandCapture.Capture> {

    private final List<Capture> captures;

    public CommandCapture(List<Capture> captures) {
        this.captures = captures;
    }

    public ActionItem<?> getResultOf(String name) {
        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .map(Capture::result)
                .orElse(null);
    }

    public Capture getCaptureOf(String name) {
        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<ActionItem.Result> getResults() {
        return captures.stream()
                .map(Capture::result)
                .map(ActionItem::getResult)
                .collect(Collectors.toList());
    }

    public int size() {
        return captures.size();
    }

    @Override
    public Iterator<Capture> iterator() {
        return captures.iterator();
    }

    public record Capture(String action, ActionItem<?> result) { }

}
