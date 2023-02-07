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
        TextComponent.Builder componentBuilder = TextComponent.builder();
        TextColor currentColorBuffer = TextColor.WHITE;
        TextDecoration currentDecoratorBuffer = null;

        int index = 0;
        int length = message.length();
        while (index < length) {
            if (message.charAt(index) == '&') {
                String code = message.substring(index, index + 2);

                //  Check if it's a color or a decorator
                ColorConversion colorConversion = ColorConversion.fromCode(code);
                DecoratorConversion decoratorConversion = DecoratorConversion.fromCode(code);

                if (colorConversion == null && decoratorConversion == null) {
                    //  Not a color or decorator, so just append the character
                    componentBuilder.append(TextComponent.of(message.charAt(index)));
                    index++;
                    continue;
                } else if (decoratorConversion == null) {
                    //  It's a color, so we update the color buffer
                    currentColorBuffer = colorConversion.getTextColor();
                } else {
                    //  It's a decorator, so we update the decorator buffer
                    currentDecoratorBuffer = decoratorConversion.getDecoration();

                    //  If it's a rest, we set the decorator to null
                    if (decoratorConversion == DecoratorConversion.RESET)
                        currentDecoratorBuffer = null;
                }

                index += 2;
            } else {
                StringBuilder builder = new StringBuilder();

                while (index < length && message.charAt(index) != '&') {
                    builder.append(message.charAt(index));
                    index++;
                }

                if (currentDecoratorBuffer != null)
                    componentBuilder.decoration(currentDecoratorBuffer, true);
                componentBuilder.color(currentColorBuffer);
                componentBuilder.append(TextComponent.of(builder.toString()));
            }
        }

        return componentBuilder.build();
    }

}
