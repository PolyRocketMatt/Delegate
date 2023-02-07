package com.github.polyrocketmatt.delegate.impl.text;

import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

public class ColorConverter {

    private enum DecoratorConversion {
        OBFUSCATED("&k", TextDecoration.OBFUSCATED),
        BOLD("&l", TextDecoration.BOLD),
        STRIKETHROUGH("&m", TextDecoration.STRIKETHROUGH),
        UNDERLINE("&n", TextDecoration.UNDERLINED),
        ITALIC("&o", TextDecoration.ITALIC),
        RESET("&r", null);

        private final String code;
        private final TextDecoration decoration;

        DecoratorConversion(String code, TextDecoration decoration) {
            this.code = code;
            this.decoration = decoration;
        }

        public String getCode() {
            return code;
        }

        public TextDecoration getDecoration() {
            return decoration;
        }

        public static DecoratorConversion fromCode(String code) {
            for (DecoratorConversion conversion : values()) {
                if (conversion.getCode().equals(code))
                    return conversion;
            }

            return null;
        }
    }

    private enum ColorConversion {
        BLACK("&0", TextColor.BLACK),
        DARK_BLUE("&1", TextColor.DARK_BLUE),
        DARK_GREEN("&2", TextColor.DARK_GREEN),
        DARK_AQUA("&3", TextColor.DARK_AQUA),
        DARK_RED("&4", TextColor.DARK_RED),
        DARK_PURPLE("&5", TextColor.DARK_PURPLE),
        GOLD("&6", TextColor.GOLD),
        GRAY("&7", TextColor.GRAY),
        DARK_GRAY("&8", TextColor.DARK_GRAY),
        BLUE("&9", TextColor.BLUE),
        GREEN("&a", TextColor.GREEN),
        AQUA("&b", TextColor.AQUA),
        RED("&c", TextColor.RED),
        LIGHT_PURPLE("&d", TextColor.LIGHT_PURPLE),
        YELLOW("&e", TextColor.YELLOW),
        WHITE("&f", TextColor.WHITE);

        private final String code;
        private final TextColor textColor;

        ColorConversion(String code, TextColor namedTextColor) {
            this.code = code;
            this.textColor = namedTextColor;
        }

        public String getCode() {
            return code;
        }

        public TextColor getTextColor() {
            return textColor;
        }

        public static ColorConversion fromCode(String code) {
            for (ColorConversion conversion : values()) {
                if (conversion.getCode().equals(code))
                    return conversion;
            }

            return null;
        }
    }

    public static TextComponent convert(String message) {
        TextComponent.Builder builder = TextComponent.builder();

        int length = message.length();
        int index = 0;
        while (index < length) {
            // If the current character is a color/decorator character, we initialise a buffer
            if (message.charAt(index) == '&') {
                String code = message.substring(index, index + 2);
                ColorConversion colorConversion = ColorConversion.fromCode(code);
                DecoratorConversion decoratorConversion = DecoratorConversion.fromCode(code);

                //  If both are valid codes, we encountered an invalid code, so we skip the next character
                if (colorConversion != null && decoratorConversion != null)
                    throw new IllegalStateException("Invalid color code: " + code);

                //  We want to walk until the next '&' character
                index += 2;
                int startWalk = index;
                while (index < length) {
                    //  Check for color/decorator combination
                    if (message.charAt(index) == '&') {
                        String doubleCode = message.substring(index, index + 2);
                        ColorConversion doubleColorConversion = ColorConversion.fromCode(doubleCode);
                        DecoratorConversion doubleDecoratorConversion = DecoratorConversion.fromCode(doubleCode);

                        //  If both are valid codes, we encountered an invalid code, so we skip the next character
                        if (doubleColorConversion != null && doubleDecoratorConversion != null)
                            throw new IllegalStateException("Invalid color code: " + code);

                        if (colorConversion != null && doubleColorConversion != null) {
                            colorConversion = doubleColorConversion;
                            index += 2;
                        }

                        else if (colorConversion != null && doubleDecoratorConversion != null) {
                            decoratorConversion = doubleDecoratorConversion;
                            index += 2;
                        }

                        else if (decoratorConversion != null && doubleColorConversion != null) {
                            colorConversion = doubleColorConversion;
                            index += 2;
                        }

                        else if (decoratorConversion != null && doubleDecoratorConversion != null) {
                            decoratorConversion = doubleDecoratorConversion;
                            index += 2;
                        }

                        //  Update walking index
                        startWalk = index;
                    } else
                        index++;
                }

                String walkedString = message.substring(startWalk, index);

                //  Append the walked string to the builder together with the color/decorator buffer
                TextComponent component = TextComponent.of(walkedString);

                // If the code is a color code, we set the color buffer
                if (colorConversion != null)
                    component.color(colorConversion.getTextColor());

                // If the code is a decorator code, we set the decorator buffer
                if (decoratorConversion != null)
                    component.decoration(decoratorConversion.getDecoration(), true);

                //  Append the component to the builder
                builder.append(component);
            } else {
                int startWalk = index;
                while (index < length && message.charAt(index) != '&')
                    index++;

                String walkedString = message.substring(startWalk, index);
                builder.append(TextComponent.of(walkedString));
            }
        }

        return builder.build();
    }

}
