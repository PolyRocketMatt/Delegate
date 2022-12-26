package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.annotations.Command;
import com.github.polyrocketmatt.delegate.core.annotations.CommandAlias;
import com.github.polyrocketmatt.delegate.core.exception.AnnotationParseException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

public class AnnotationHandler implements Handler {

    public void process(Object object) {
        //  Process object's annotations into a command tree
        Annotation[] annotations = object.getClass().getAnnotations();

        Optional<Command> oCommand = getAnnotation(annotations);
        Optional<CommandAlias> oCommandAlias = getAnnotation(annotations);

        if (oCommand.isEmpty())
            throw new AnnotationParseException("Command annotation not found on class " + object.getClass().getName());

        //  TODO: Continue parsing command
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof Command)
                .findFirst()
                .map(annotation -> (T) annotation);
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
