// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.configuration;

import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DelegateConfiguration {
    private final Map<FeedbackType, String> feedbackMessages;

    public DelegateConfiguration() {
        //  Initialize feedback messages
        this.feedbackMessages = new HashMap<>(Arrays
                .stream(FeedbackType.values())
                .map(type -> Map.entry(type, type.getDefaultMessage()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public DelegateConfiguration setFeedback(FeedbackType type, String message) {
        this.feedbackMessages.put(type, message);
        return this;
    }

    public String get(FeedbackType type) {
        return this.feedbackMessages.get(type);
    }

}
