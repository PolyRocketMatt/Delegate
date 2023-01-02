package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.CommandBuffer;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.Index;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;
import static java.lang.Math.min;

/**
 * A {@link Function}-based command action that takes arguments and yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FunctionAction extends CommandAction {

    private final BiFunction<CommanderEntity, Index, ?> action;

    /**
     * Creates a new {@link FunctionAction} with an identifier and {@link Function} and
     * a default precedence of 0.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link Function}.
     *
     * @param precedence The precedence of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link Function}.
     *
     * @param identifier The identifier of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(String identifier, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link Function}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The {@link Function} that will be executed.
     */
    public FunctionAction(String identifier, int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, precedence);
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, CommandBuffer<CommandArgument<?>> arguments, List<String> inputs) {
        int maxIndex = min(inputs.size(), arguments.size());

        List<Argument<?>> inputItems = new ArrayList<>();
        for (int i = 0; i < maxIndex; i++)
            inputItems.add(arguments.get(i).parse(inputs.get(i)));
        try {
            //  Convert to index
            Index index = new Index(inputItems);

            return new ActionItem<>(ActionItem.Result.SUCCESS, action.apply(commander, index));
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }
    }

}
