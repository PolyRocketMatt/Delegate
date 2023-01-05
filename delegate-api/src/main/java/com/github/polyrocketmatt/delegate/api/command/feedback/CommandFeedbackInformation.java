package com.github.polyrocketmatt.delegate.api.command.feedback;

public record CommandFeedbackInformation(
        String feedback,
        FeedbackType type,
        Object[] args
) {

    public String getFormattedFeedback() {
        return this.feedback.formatted(args);
    }

}