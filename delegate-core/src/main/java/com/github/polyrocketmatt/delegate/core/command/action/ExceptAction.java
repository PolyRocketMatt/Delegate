// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.Bufferable;
import com.github.polyrocketmatt.delegate.api.TriConsumer;
import com.github.polyrocketmatt.delegate.api.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import java.util.List;
import java.util.function.BiConsumer;

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
    public ExceptAction(TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        super(newId());
        if (action == null)
            throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    /**
     * Runs the action with the given commander, feedback type and arguments.
     *
     * @param commander The commander that executed the command.
     * @param type The feedback type.
     * @param arguments The arguments of the command.
     */
    public void run(CommanderEntity commander,  FeedbackType type, List<String> arguments) {
        if (commander == null)
            throw new IllegalArgumentException("Commander cannot be null");
        if (type == null)
            throw new IllegalArgumentException("Feedback type cannot be null");
        if (arguments == null)
            throw new IllegalArgumentException("Arguments cannot be null");

        try {
            action.accept(commander, type, arguments);
        } catch (Exception ex) {
            if (getDelegate().isVerbose())
                ex.printStackTrace();
        }
    }

}
