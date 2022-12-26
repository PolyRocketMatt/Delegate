package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.ArgumentRuleResult;
import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;

import java.lang.reflect.Field;

public class DefaultRule<T> extends ArgumentRule<String, T> {

    public DefaultRule(T defaultValue) {
        super(input -> new RuleData<>(defaultValue));
    }

    @Override
    public ArgumentRuleResult interpretResult(CommandArgument<?> argument, RuleData<String> input, RuleData<?> output) {
        if (input.value() == null) {
            ActionItem<?> actionItem = new ActionItem<>(output.value());

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
