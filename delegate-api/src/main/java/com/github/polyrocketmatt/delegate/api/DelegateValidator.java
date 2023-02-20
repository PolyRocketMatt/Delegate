package com.github.polyrocketmatt.delegate.api;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class DelegateValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static void validate(Object object) {
        validator.validate(object);
    }

    public static void validate(Object... objects) {
        for (Object object : objects)
            validate(object);
    }

}
