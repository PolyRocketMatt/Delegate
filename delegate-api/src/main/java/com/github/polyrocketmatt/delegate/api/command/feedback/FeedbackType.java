// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api.command.feedback;

import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

@API(status = API.Status.STABLE, since = "0.0.1")
public enum FeedbackType {

    //  Command Related
    COMMAND_NON_EXISTENT("Command %s does not exist."),
    COMMAND_UNVERIFIED("Command %s is not verified and therefor not allowed to be executed."),

    UNAUTHORIZED("You do not have permission to execute the command: %s"),

    ACTIONS_NULL("The actions list is null for command %s."),

    ARGS_INVALID_COUNT("Wrong number of arguments. Expected %d, got %d."),
    ARGS_INVALID_IDENTIFIER("Invalid argument identifier: %s."),
    ARGS_INVALID_FORMAT("Wrong argument format for argument %s. Expected %s, got %s."),
    ARGS_INVALID_PARSE_RESULT("Argument %s could not successfully be parsed to %s."),
    ARGS_DUPLICATE_IDENTIFIER("Duplicate argument identifier: %s."),
    ARGS_INVALID_TYPE("Invalid argument type for argument %s: %s."),

    BRIGADIER_SYNTAX("%s"),
    BRIGADIER_EXCEPTION("An exception occurred while executing the command using Brigadier: %s");

    private final String defaultMessage;

    FeedbackType(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public @NotNull String getDefaultMessage() {
        return defaultMessage;
    }

}
