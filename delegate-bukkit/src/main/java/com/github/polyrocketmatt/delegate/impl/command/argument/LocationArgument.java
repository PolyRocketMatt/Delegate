package com.github.polyrocketmatt.delegate.impl.command.argument;

import com.github.polyrocketmatt.delegate.api.command.argument.Argument;
import com.github.polyrocketmatt.delegate.api.command.argument.CommandArgument;
import com.github.polyrocketmatt.delegate.api.command.argument.rule.RuleFormat;
import com.github.polyrocketmatt.delegate.api.exception.ArgumentParseException;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class LocationArgument extends CommandArgument<Location> {

    private final LocationFormat format;

    public LocationArgument(String argumentDescription, LocationFormat format) {
        super(argumentDescription);
        this.format = format;
    }

    public LocationArgument(String identifier, String argumentDescription, LocationFormat format) {
        super(identifier, argumentDescription);
        this.format = format;
    }

    @Override
    public Argument<Location> parse(String input, Consumer<Exception> consumer) {
        if (format.getRuleFormat().matches(input))
            return new Argument<>(getIdentifier(), format.getRuleFormat().parse(input));
        else
            throw onFail(input, new ArgumentParseException("The input '" + input + "' does not match the required format."));
    }

    @Override
    public ArgumentParseException onFail(String input, Exception wrappedException) {
        return super.onFail(input, wrappedException);
    }

    public enum LocationFormat {
        SIMPLE_DOT(Pattern.compile("-?\\d+\\.-?\\d+\\.-?\\d+")),
        SIMPLE_COMMA(Pattern.compile("-?\\d+,-?\\d+,-?\\d+")),
        VERBOSE(Pattern.compile("\\[x=\\d+,y=\\d+,z=\\d+\\]")),
        WORLD_VERBOSE(Pattern.compile("\\[world=[a-zA-Z0-9],x=\\d+,y=\\d+,z=\\d+\\]"));

        private final Pattern format;

        LocationFormat(Pattern format) {
            this.format = format;
        }

        public RuleFormat<Location> getRuleFormat() {
            return new RuleFormat<>() {
                @Override
                public boolean matches(String input) {
                    return format.matcher(input.replaceAll(" ", "")).matches();
                }

                @Override
                public Location parse(String input) {
                    return switch (values()[ordinal()]) {
                        case SIMPLE_DOT -> {
                            String[] split = input.split("\\.");
                            yield new Location(null,
                                    Integer.parseInt(split[0]),
                                    Integer.parseInt(split[1]),
                                    Integer.parseInt(split[2])
                            );
                        }

                        case SIMPLE_COMMA -> {
                            String[] split = input.split("\\-");
                            yield new Location(null,
                                    Integer.parseInt(split[0]),
                                    Integer.parseInt(split[1]),
                                    Integer.parseInt(split[2])
                            );
                        }

                        case VERBOSE -> {
                            String[] split = input.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                            yield new Location(null,
                                    Integer.parseInt(split[0].split("=")[1]),
                                    Integer.parseInt(split[1].split("=")[1]),
                                    Integer.parseInt(split[2].split("=")[1])
                            );
                        }

                        case WORLD_VERBOSE -> {
                            String[] split = input.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                            yield new Location(Bukkit.getWorld(split[0].split("=")[1]),
                                    Integer.parseInt(split[1].split("=")[1]),
                                    Integer.parseInt(split[2].split("=")[1]),
                                    Integer.parseInt(split[3].split("=")[1])
                            );
                        }
                    };
                }
            };
        }


    }

}
