package com.github.polyrocketmatt.delegate.core.command.argument.range;

public class DelegateIntRange extends DelegateRange<Integer> {

    public DelegateIntRange(int min, int max) {
        super(min, max);
    }

    @Override
    public boolean contains(Integer value) {
        return value >= getMin() && value <= getMax();
    }

}
