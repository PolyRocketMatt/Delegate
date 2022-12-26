package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import com.github.polyrocketmatt.delegate.core.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;

import java.util.Objects;

public class NonNullRule extends ArgumentRule<String, Boolean> {

    public NonNullRule() {
        super(input -> new RuleOutput<>(Objects.nonNull(input)));
    }

    @Override
    public void interpretResult(CommandArgument<?> argument, RuleInput<String> input, RuleOutput<?> output) {
        if (!(output.result() instanceof Boolean result))
            throw new ArgumentParseException("Expected result of rule did not match");
        if (!result)
            throw new ArgumentParseException(argument.onFail(input.input(), null));
    }

}
