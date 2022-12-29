package com.github.polyrocketmatt.delegate.api.command.argument.rule;

public interface RuleFormat<T> {

    boolean matches(String input);

    T parse(String input);

}
