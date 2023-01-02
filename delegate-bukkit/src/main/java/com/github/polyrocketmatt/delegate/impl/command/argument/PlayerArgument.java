package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class PlayerArgument extends CommandArgument<Player> {

    private final String name;

    public PlayerArgument(String argumentDescription, String name) {
        super(argumentDescription);
        this.name = name;
    }

    public PlayerArgument(String identifier, String argumentDescription, String name) {
        super(identifier, argumentDescription);
        this.name = name;
    }

    @Override
    public Argument<Player> parse(String input, Consumer<Exception> consumer) {
        Player player = Bukkit.getPlayer(input);
        if (player != null)
            return new Argument<>(getIdentifier(), player);
        else
            throw onFail(input, new ArgumentParseException("The player '" + input + "' does not exist."));
    }

    @Override
    public ArgumentParseException onFail(String input, Exception wrappedException) {
        return super.onFail(input, wrappedException);
    }

}
