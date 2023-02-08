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
     */
    public ExceptAction(TriConsumer<CommanderEntity, FeedbackType, List<String>> action) {
        super(newId());
        this.action = action;
    }


    public void run(CommanderEntity commander,  FeedbackType type, List<String> arguments) {
        try {
            action.accept(commander, type, arguments);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
