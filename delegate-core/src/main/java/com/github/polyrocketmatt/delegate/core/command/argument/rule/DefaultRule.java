package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;

import java.lang.reflect.Field;

public class DefaultRule<T> extends ArgumentRule<String, T> {

    public DefaultRule(T defaultValue) {
        super(input -> new RuleOutput<>(defaultValue));
    }

    @Override
    public void interpretResult(CommandArgument<?> argument, RuleInput<String> input, RuleOutput<?> output) {
        if (input.input() == null) {
            ActionItem<?> actionItem = new ActionItem<>(output.result());

            try {
                //  TODO: Fix this hacky thing
                Field defaultArgField = argument.getClass().getField("defaultValue");

                defaultArgField.setAccessible(true);
                defaultArgField.set(argument, actionItem);
                defaultArgField.setAccessible(false);
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                throw new ArgumentParseException("Argument does not have a default value", ex);
            }
        }
    }
}
