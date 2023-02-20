// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.configuration;

import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

public class DelegateConfiguration {
    private final Map<FeedbackType, String> feedbackMessages;

    public DelegateConfiguration() {
        //  Initialize feedback messages
        this.feedbackMessages = new HashMap<>(Arrays
                .stream(FeedbackType.values())
                .map(type -> Map.entry(type, type.getDefaultMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public DelegateConfiguration setFeedback(@NotNull FeedbackType type, @NotNull String message) {
        validate(type, message);

        this.feedbackMessages.put(type, message);
        return this;
    }

    public String get(@NotNull FeedbackType type) {
        validate(type);

        return this.feedbackMessages.get(type);
    }

}
