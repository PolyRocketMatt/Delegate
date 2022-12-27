package com.github.polyrocketmatt.delegate.core.command.argument.rule;

/**
 * Represents the result of an argument rule.
 *
 * @param result The result of the rule.
 * @param info Additional information about the result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public record ArgumentRuleResult(Result result, String info) {

    /**
     * Enum representing the possible result of applying an argument rule.
     */
    public enum Result {
        SUCCESS,
        FAILURE
    }

}
