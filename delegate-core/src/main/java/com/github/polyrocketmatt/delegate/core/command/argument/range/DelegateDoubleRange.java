package com.github.polyrocketmatt.delegate.core.command.argument.range;

public class DelegateDoubleRange extends DelegateRange<Double> {

    public DelegateDoubleRange(Double min, Double max) {
        super(min, max);
    }

    @Override
    public boolean contains(Double value) {
        return value >= getMin() && value <= getMax();
    }
}
