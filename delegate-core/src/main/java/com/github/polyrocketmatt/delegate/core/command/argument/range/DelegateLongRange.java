package com.github.polyrocketmatt.delegate.core.command.argument.range;

public class DelegateLongRange extends DelegateRange<Long> {

    public DelegateLongRange(Long min, Long max) {
        super(min, max);
    }

    @Override
    public boolean contains(Long value) {
        return value >= getMin() && value <= getMax();
    }

}
