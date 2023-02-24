package com.github.polyrocketmatt.delegate.api;

public class DelegateValidator {

    public static <T> void validate(String variable, Class<T> clazz, T object) {
        if (object == null)
            throw new IllegalArgumentException("%s (%s) cannot be null".formatted(variable, clazz.getSimpleName()));
    }

}
