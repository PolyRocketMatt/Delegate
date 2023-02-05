package com.github.polyrocketmatt.delegate.core.command.action;

import com.github.polyrocketmatt.delegate.api.command.action.CommandAction;
import com.github.polyrocketmatt.delegate.api.command.argument.Index;
import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.data.FailureActionResult;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.command.permission.PermissionTier;
import com.github.polyrocketmatt.delegate.core.permission.PermissionTiers;

import java.util.List;
import java.util.function.BiFunction;

import static com.github.polyrocketmatt.delegate.api.StringUtils.newId;

/**
 * A {@link BiFunction}-based command action that takes arguments and yields a result.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public class FunctionAction extends CommandAction {

    private final BiFunction<CommanderEntity, Index, ?> action;

    /**
     * Creates a new {@link FunctionAction} with an identifier and {@link BiFunction} and
     * a default precedence of 0.
     *
     * @param action The function that will be executed.
     */
    public FunctionAction(BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), PermissionTiers.GLOBAL.getTier(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier and {@link BiFunction} and
     * a default precedence of 0.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param action The function that will be executed.
     */
    public FunctionAction(PermissionTier permissionTier, BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), permissionTier, 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     */
    public FunctionAction(int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), PermissionTiers.GLOBAL.getTier(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     */
    public FunctionAction(PermissionTier permissionTier, int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(newId(), permissionTier, precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param action The function that will be executed.
     */
    public FunctionAction(String identifier, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, PermissionTiers.GLOBAL.getTier(), 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param action The function that will be executed.
     */
    public FunctionAction(String identifier, PermissionTier permissionTier, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, permissionTier, 0);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     */
    public FunctionAction(String identifier, int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, PermissionTiers.GLOBAL.getTier(), precedence);
        this.action = action;
    }

    /**
     * Creates a new {@link FunctionAction} with an identifier, precedence and {@link BiFunction}.
     *
     * @param identifier The identifier of the command action.
     * @param permissionTier The permission tier required to execute this action.
     * @param precedence The precedence of the command action.
     * @param action The function that will be executed.
     */
    public FunctionAction(String identifier, PermissionTier permissionTier, int precedence, BiFunction<CommanderEntity, Index, ?> action) {
        super(identifier, permissionTier, precedence);
        this.action = action;
    }

    @Override
    public ActionItem<?> run(CommanderEntity commander, List<Argument<?>> arguments) {
        try {
            return new ActionItem<>(ActionItem.Result.SUCCESS, action.apply(commander, new Index(arguments)));
        } catch (Exception ex) {
            return new FailureActionResult(ex);
        }
    }

}
