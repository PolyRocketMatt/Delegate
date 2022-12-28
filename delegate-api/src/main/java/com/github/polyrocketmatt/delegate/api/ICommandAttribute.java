package com.github.polyrocketmatt.delegate.api;

public interface ICommandAttribute {

    default AttributeType getType() {
        return AttributeType.UNKNOWN;
    }

}
