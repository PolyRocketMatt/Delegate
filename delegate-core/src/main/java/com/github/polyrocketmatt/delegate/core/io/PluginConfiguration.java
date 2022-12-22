package com.github.polyrocketmatt.delegate.core.io;

import com.github.polyrocketmatt.delegate.core.exception.PluginException;

import java.io.File;
import java.io.IOException;

import static com.github.polyrocketmatt.delegate.core.Delegate.getDelegate;
import static com.github.polyrocketmatt.delegate.core.io.FileHandler.PLUGIN_CONFIG_PATH;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Class that holds the configuration Delegate will use for a plugin.
 */
public class PluginConfiguration implements PluginHolder {

    private final String name;
    private final String path;
    private final File file;
    private final PluginConfigurationFile configuration;

    /**
     * Create a new PluginConfiguration instance for the given plugin.
     *
     * @param name The plugin name to initialize the configuration for.
     */
    public PluginConfiguration(String name) {
        try {
            this.name = name;
            this.path = "%s%s.pcf".formatted(PLUGIN_CONFIG_PATH, name);

            System.out.printf("Path: %s%n", path);

            this.file = FileHandler.getFile(getDelegate().dataFolder(), path);
            this.configuration = loadConfiguration();
        } catch (PluginException ex) {
            //  TODO: Log this error
            throw new RuntimeException(ex);
        }
    }

    private PluginConfigurationFile loadConfiguration() throws PluginException {
        try {
            PluginConfigurationFile config;
            if (!FileHandler.exists(this.file)) {
                System.out.println("Should create file?");

                FileHandler.create(this.file);

                config = PluginConfigurationFile.getDefaultConfig(this.name);
                config.exportTo(this.file);
            } else {
                config = new PluginConfigurationFile().importFrom(this.file);
            }

            return config;
        } catch (IOException ex) {
            throw new PluginException("Could not load/create configuration for plugin %s".formatted(name), ex);
        }
    }

    /**
     * Saves the configuration to the file.
     *
     * @throws IOException If the configuration could not be saved.
     */
    public void saveConfiguration() throws IOException {
        this.configuration.exportTo(this.file);
    }

    /**
     * Gets the plugin's name this configuration is for.
     *
     * @return The plugin's name this configuration is for.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the {@link PluginConfigurationFile} instance for this configuration.
     *
     * @return The {@link PluginConfigurationFile} instance for this configuration.
     */
    public PluginConfigurationFile getConfiguration() {
        return this.configuration;
    }

    @Override
    public boolean owns(String pluginName) {
        return this.name.equals(pluginName);
    }

}
