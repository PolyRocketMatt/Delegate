package com.github.polyrocketmatt.delegate.core.command.argument;

public record ArgumentRuleResult(Result result, String info) {

    public enum Result {
        SUCCESS,
        FAILURE
    }

}
