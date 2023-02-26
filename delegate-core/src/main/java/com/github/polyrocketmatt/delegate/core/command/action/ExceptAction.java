// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.AttributeType;
import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.TriConsumer;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

import static com.github.polyrocketmatt.delegate.api.DelegateValidator.validate;
import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static com.github.polyrocketmatt.delegate.core.DelegateCore.getDelegate;

/**
 * A {@link TriConsumer}-based command action that is only executed when an
 * exception is thrown after validation of the command.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ExceptAction extends CommandAttribute implements Bufferable {

    private final TriConsumer<CommanderEntity, FeedbackType, List<String>> action;

    /**
     * Creates a new {@link ConsumerAction} with an identifier and {@link BiConsumer} and
     * a default precedence of 0.
     *
     * @param action The consumer that will be executed.
     * @throws IllegalArgumentException If the action is null.
     */
    public ExceptAction(@NotNull TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        super(newId());
        this.action = action;
    }

    /**
     * Runs the action with the given commander, feedback type and arguments.
     *
     * @param commander The commander that executed the command.
     * @param type The feedback type.
     * @param arguments The arguments of the command.
     */
    public void run(@NotNull CommanderEntity commander, @NotNull FeedbackType type, @NotNull List<String> arguments) {
        validate("commander", CommanderEntity.class, commander);
        validate("type", FeedbackType.class, type);
        validate("arguments", List.class, arguments);
        for (String argument : arguments)
            validate("argument", String.class, argument);

        try {
            action.accept(commander, type, arguments);
        } catch (Exception ex) {
            if (getDelegate().isVerbose())
                ex.printStackTrace();
        }
    }

    @Override
    public @NotNull AttributeType getType() {
        return AttributeType.EXCEPT_ACTION;
    }
}
