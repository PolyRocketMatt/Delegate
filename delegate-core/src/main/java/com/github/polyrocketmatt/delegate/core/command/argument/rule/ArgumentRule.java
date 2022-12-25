package com.github.polyrocketmatt.delegate.core.command.argument.rule;

import java.util.function.Function;

public abstract class ArgumentRule<K, V> {

    private final Function<K, V> rule;

    public ArgumentRule(Function<K, V> rule) {
        this.rule = rule;
    }

    public Function<K, V> getRule() {
        return rule;
    }

    public V test(K input) {
        return rule.apply(input);
    }

}
