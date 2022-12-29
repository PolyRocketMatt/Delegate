package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleData;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.ActionResult;

import java.lang.reflect.Field;

/**
 * A rule that assigns a default value to an argument if no input string
 * for the argument is present.
 *
 * @param <T> The type of the argument.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class DefaultRule<T> extends ArgumentRule<String, T> {

    /**
     * Creates a new rule that assigns a default value to an argument if it is not present.
     *
     * @param defaultValue The default value to assign.
     */
    public DefaultRule(T defaultValue) {
        super(input -> new RuleData<>(defaultValue));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleData<String> input, RuleData<?> output) {
        if (input.value() == null) {
            ActionItem<?> actionItem = new ActionItem<>(ActionResult.Result.SUCCESS, output.value());

            try {
                //  TODO: Fix this hacky thing
                Field defaultArgField = argument.getClass().getField("defaultValue");

                defaultArgField.setAccessible(true);
                defaultArgField.set(argument, actionItem);
                defaultArgField.setAccessible(false);

                return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                return new ArgumentRuleResult(ArgumentRuleResult.Result.FAILURE, "Argument already has a value");
            }
        }

        return new ArgumentRuleResult(ArgumentRuleResult.Result.SUCCESS, "Successfully passed %s".formatted(getClass().getSimpleName()));
    }
}
