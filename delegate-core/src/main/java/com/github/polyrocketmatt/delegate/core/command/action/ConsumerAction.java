package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.Index;
import com.github.polyrocketmatt.delegate.api.command.data.ActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.command.data.SuccessActionResult;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static java.lang.Math.min;

/**
 * A {@link Consumer}-based command action that does not yield a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class ConsumerAction extends CommandAction {

    private final BiConsumer<CommanderEntity, Index> action;

    /**
     * Creates a new {@link ConsumerAction} with an identifier and {@link Consumer} and
     * a default precedence of 0.
     *
     * @param action The {@link Consumer} that will be executed.
     */
    public ConsumerAction(BiConsumer<CommanderEntity, Index> action) {
        super(newId(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link Consumer}.
     *
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public ConsumerAction(int precedence, BiConsumer<CommanderEntity, Index> action) {
        super(newId(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link ConsumerAction} with an identifier, precedence and {@link Consumer}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Consumer} that will be executed.
     */
    public ConsumerAction(String identifier, int precedence, BiConsumer<CommanderEntity, Index> action) {
        super(identifier, precedence);
        this.action = action;
    }

    @Override
    public ActionResult run(CommanderEntity commander, CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        int maxIndex = min(inputs.size(), arguments.size());

        List<Argument<?>> inputItems = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++)
            inputItems.add(arguments.get(i).parse(inputs.get(i)));
        try {
            //  Convert to index
            Index index = new Index(inputItems);

            action.accept(commander, index);
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }

        return new SuccessActionResult();
    }

}
