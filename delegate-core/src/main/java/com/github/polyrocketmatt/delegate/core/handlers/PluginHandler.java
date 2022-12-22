package com.github.polyrocketmatt.delegate.core.handlers;

import com.github.polyrocketmatt.delegate.core.exception.HandlerException;
import com.github.polyrocketmatt.delegate.core.io.PluginConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Handler that handles the registration of plugins and their configuration.
 */
public class PluginHandler implements Handler {

    private final List<PluginConfiguration> configurations;

    /**
     * Create a new PluginHandler instance.
     */
    public PluginHandler() {
        configurations = new ArrayList<>();
    }

    /**
     * Register a plugin to Delegate.
     *
     * @param pluginName The plugin name to register.
     * @throws NullPointerException If the plugin is null.
     * @throws HandlerException If the plugin is already registered.
     */
    public void registerPlugin(String pluginName) {
        if (pluginName == null)
            throw new NullPointerException("Plugin cannot be null");
        if (configurations.stream().anyMatch(configuration -> configuration.owns(pluginName)))
            throw new HandlerException("Plugin is already registered");

        configurations.add(new PluginConfiguration(pluginName));
    }

    /**
     * Unregister a plugin from Delegate.
     *
     * @param pluginName The plugin to unregister.
     * @throws NullPointerException If the plugin is null.
     */
    public void unregisterPlugin(String pluginName) {
        if (pluginName == null)
            throw new NullPointerException("Plugin cannot be null");

        configurations.removeIf(configuration -> configuration.owns(pluginName));
    }

    /**
     * Get the configuration for the given plugin.
     *
     * @param pluginName The plugin to get the configuration for.
     * @return The configuration for the given plugin.
     * @throws NullPointerException If the plugin is null.
     * @throws HandlerException If the plugin is not registered.
     */
    public PluginConfiguration getConfiguration(String pluginName) {
        if (pluginName == null)
            throw new NullPointerException("Plugin cannot be null");
        return configurations
                .stream()
                .filter(config -> config.owns(pluginName))
                .findFirst()
                .orElseThrow(() -> new HandlerException("Plugin is not registered"));
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {
        configurations.forEach(configuration -> {
            try {
                configuration.saveConfiguration();
            } catch (IOException ex) {
                throw new HandlerException("Failed to save configuration for plugin " + configuration.getName(), ex);
            }
        });
        configurations.clear();
    }

}
