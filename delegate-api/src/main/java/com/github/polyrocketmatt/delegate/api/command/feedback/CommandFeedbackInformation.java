// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.feedback;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public record CommandFeedbackInformation(@NotNull String feedback, @NotNull FeedbackType type, @NotNull Object[] args) {

    public CommandFeedbackInformation {
        validate("feedback", String.class, feedback);
        validate("type", FeedbackType.class, type);
        validate("args", Object[].class, args);
        Arrays.stream(args).forEach(arg -> validate("argument", Object.class, arg));
    }

    public @NotNull String getFormattedFeedback() {
        return this.type.getDefaultMessage().formatted(args);
    }

}