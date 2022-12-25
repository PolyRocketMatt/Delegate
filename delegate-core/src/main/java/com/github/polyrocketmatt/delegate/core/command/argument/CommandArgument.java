package com.github.polyrocketmatt.delegate.core.command.argument;

import com.github.polyrocketmatt.delegate.core.command.CommandAttribute;
import com.github.polyrocketmatt.delegate.core.command.argument.rule.ArgumentRule;
import com.github.polyrocketmatt.delegate.core.data.ActionItem;
import com.github.polyrocketmatt.delegate.core.exception.ArgumentParseException;
import com.github.polyrocketmatt.delegate.core.utils.Bufferable;

import java.util.List;
import java.util.function.Consumer;

public abstract class CommandArgument<T> extends CommandAttribute implements Bufferable {

    private final String argumentDescription;
    private final List<ArgumentRule<?, ?>> argumentRules;

    public CommandArgument(String identifier, String argumentDescription) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = List.of();
    }

    public CommandArgument(String identifier, String argumentDescription, List<ArgumentRule<?, ?>> argumentRules) {
        super(identifier);
        this.argumentDescription = argumentDescription;
        this.argumentRules = argumentRules;
    }

    public String getArgumentDescription() {
        return argumentDescription;
    }

    public List<ArgumentRule<?, ?>> getArgumentRules() {
        return argumentRules;
    }

    public ActionItem<T> parse(String input) {
        return parse(input, ex -> {
            throw new ArgumentParseException("Failed to parse argument \"%s\"".formatted(input), ex);
        });
    }

    public abstract ActionItem<T> parse(String input, Consumer<Exception> onFail);

}
