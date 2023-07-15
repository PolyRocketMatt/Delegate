package com.github.polyrocketmatt.delegate.core.command.argument.range;

public class DelegateFloatRange extends DelegateRange<Float> {

    public DelegateFloatRange(float min, float max) {
        super(min, max);
    }

    @Override
    public boolean contains(Float value) {
        return value >= getMin() && value <= getMax();
    }

}
