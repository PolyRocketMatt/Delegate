// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.exception;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.feedback.CommandFeedbackInformation;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;

/**
 * Exception thrown when the execution of a {@link com.github.polyrocketmatt.delegate.api.command.IDelegateCommand} fails.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class CommandExecutionException extends RuntimeException {

    private final CommandDispatchInformation dispatchInformation;
    private final CommandFeedbackInformation feedbackInformation;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param dispatch The {@link CommandDispatchInformation} that was used to dispatch the command.
     * @param feedback The feedback that should be sent to the {@link CommanderEntity}.
     * @param type The {@link FeedbackType} that should be used to send the feedback.
     * @param args The arguments that should be used to format the feedback.
     */
    public CommandExecutionException(CommandDispatchInformation dispatch, String feedback, FeedbackType type, Object[] args) {
        super(feedback.formatted(args));

        validate(dispatch, feedback, type, args);

        this.dispatchInformation = dispatch;
        this.feedbackInformation = new CommandFeedbackInformation(feedback, type, args);
    }

    public CommanderEntity getCommander() {
        return this.dispatchInformation.commander();
    }

    public String getCommand() {
        return this.dispatchInformation.command();
    }

    public String[] getArguments() {
        return this.dispatchInformation.arguments();
    }

    public FeedbackType getFeedbackType() {
        return this.feedbackInformation.type();
    }

    public String getFeedback() {
        return this.feedbackInformation.getFormattedFeedback();
    }

}
