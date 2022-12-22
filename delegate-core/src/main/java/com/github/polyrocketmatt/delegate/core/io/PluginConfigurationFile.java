package com.github.polyrocketmatt.delegate.core.io;

import com.github.polyrocketmatt.delegate.core.exception.PluginException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Class that represents a platform-agnostic plugin configuration
 * for Delegate.
 */
public class PluginConfigurationFile implements Configurable<PluginConfigurationFile> {

    private String name;
    private String version;
    private String prefix;
    private String base;

    private boolean usePrefix;
    private boolean useBase;

    /**
     * Get the name of the plugin this configuration belongs to.
     *
     * @return The name of the plugin this configuration belongs to.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the plugin this configuration belongs to.
     *
     * @param name The name of the plugin this configuration belongs to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the version of the plugin this configuration belongs to.
     *
     * @return The version of the plugin this configuration belongs to.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version of the plugin this configuration belongs to.
     *
     * @param version The version of the plugin this configuration belongs to.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get the prefix of the plugin this configuration belongs to.
     *
     * @return The prefix of the plugin this configuration belongs to.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Set the prefix of the plugin this configuration belongs to.
     *
     * @param prefix The prefix of the plugin this configuration belongs to.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Get the base command of the plugin this configuration belongs to.
     *
     * @return The base command of the plugin this configuration belongs to.
     */
    public String getBase() {
        return base;
    }

    /**
     * Set the base command of the plugin this configuration belongs to.
     *
     * @param base The base command of the plugin this configuration belongs to.
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * Get whether the plugin this configuration belongs to uses a prefix.
     *
     * @return Whether the plugin this configuration belongs to uses a prefix.
     */
    public boolean isUsePrefix() {
        return usePrefix;
    }

    /**
     * Set whether the plugin this configuration belongs to uses a prefix.
     *
     * @param usePrefix Whether the plugin this configuration belongs to uses a prefix.
     */
    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    /**
     * Get whether the plugin this configuration belongs to uses a base command.
     *
     * @return Whether the plugin this configuration belongs to uses a base command.
     */
    public boolean isUseBase() {
        return useBase;
    }

    /**
     * Set whether the plugin this configuration belongs to uses a base command.
     *
     * @param useBase Whether the plugin this configuration belongs to uses a base command.
     */
    public void setUseBase(boolean useBase) {
        this.useBase = useBase;
    }

    /**
     * Creates a new PluginYaml instance with default values given the plugin name.
     *
     * @param name The name of the plugin this configuration belongs to.
     * @return A new PluginYaml instance with default values.
     */
    public static PluginConfigurationFile getDefaultConfig(String name) {
        PluginConfigurationFile config = new PluginConfigurationFile();
        config.setName(name);
        config.setVersion(getDelegate().getDelegateVersion());
        config.setPrefix("[%s]".formatted(name));
        config.setBase("%s".formatted(name.toLowerCase()));

        return config;
    }

    @Override
    public PluginConfigurationFile importFrom(File file) throws IOException {
        String content = Files.readString(file.toPath());
        String[] lines = content.split("\n");

        System.out.printf("Lines: %s", lines.length);
        for (String line : lines)
            System.out.printf("Line: %s", line);

        for (String line : lines) {
            //  Split the line into field and value
            String[] split = line.split(":");
            String field = split[0];
            String value = split[1];

            //  Get the field from the config
            try {
                Field configField = this.getClass().getDeclaredField(field);
                configField.setAccessible(true);

                //  Get the type of the field
                Class<?> type = configField.getType();

                //  Try casting the value to the type of the field
                try {
                    Object castedValue = type.cast(value);
                    configField.set(this, castedValue);
                } catch (ClassCastException e) {
                    throw new PluginException("Could not cast value '%s' to type '%s'".formatted(value, type.getName()));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new PluginException("Could not set field '%s' to value '%s'".formatted(field, value));
            }
        }

        return null;
    }

    @Override
    public void exportTo(File file) throws IOException {
        StringBuilder builder = new StringBuilder();

        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                builder.append(field.getName()).append(": ").append(field.get(this)).append("\n");
            } catch (IllegalAccessException ex) {
                throw new PluginException("Could not access field '%s' in PluginConfigurationFile class.".formatted(field.getName()));
            }
        }

        //  Write the content of the builder to the file
        Files.write(file.toPath(), builder.toString().getBytes());
    }

}