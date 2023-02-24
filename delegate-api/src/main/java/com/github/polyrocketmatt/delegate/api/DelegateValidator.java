package com.github.polyrocketmatt.delegate.api;

import org.apiguardian.api.API;

@API(status = API.Status.STABLE, since = "0.0.1")
public class DelegateValidator {

    public static <T> void validate(String variable, Class<T> clazz, T object) {
        if (object == null)
            throw new IllegalArgumentException("%s (%s) cannot be null".formatted(variable, clazz.getSimpleName()));
    }

}
