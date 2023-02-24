// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public class CommandCapture implements Iterable<CommandCapture.Capture> {

    private final List<Capture> captures;

    public CommandCapture(@NotNull List<Capture> captures) {
        validate("captures", List.class, captures);
        captures.forEach(capture -> validate("element", Capture.class, capture));

        this.captures = captures;
    }

    public @Nullable ActionItem<?> getResultOf(@NotNull String name) {
        validate("name", String.class, name);

        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .map(Capture::result)
                .orElse(null);
    }

    public @Nullable Capture getCaptureOf(@NotNull String name) {
        validate("name", String.class, name);

        return captures.stream()
                .filter(capture -> capture.action().equals(name))
                .findFirst()
                .orElse(null);
    }

    public @NotNull List<ActionItem.Result> getResults() {
        return captures.stream()
                .map(Capture::result)
                .map(ActionItem::getResult)
                .collect(Collectors.toList());
    }

    public int size() {
        return captures.size();
    }

    @Override
    public @NotNull Iterator<Capture> iterator() {
        return captures.iterator();
    }

    public record Capture(@NotNull String action, @NotNull ActionItem<?> result) {

        public Capture {
            validate("action", String.class, action);
            validate("result", ActionItem.class, result);
        }

    }

}
