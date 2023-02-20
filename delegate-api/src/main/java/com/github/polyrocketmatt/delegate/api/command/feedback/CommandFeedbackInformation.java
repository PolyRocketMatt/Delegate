// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.feedback;

public record CommandFeedbackInformation(
        String feedback,
        FeedbackType type,
        Object[] args
) {

    public String getFormattedFeedback() {
        return this.type.getDefaultMessage().formatted(args);
    }

}