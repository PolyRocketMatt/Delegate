package com.github.polyrocketmatt.delegate.core.io;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Interface that defines a holder for a plugin.
 */
@FunctionalInterface
public interface PluginHolder {

    /**
     * Checks if the given plugin name is the same as the one held by this instance.
     *
     * @param pluginName The plugin name to check.
     * @return True if the plugin is the same, false otherwise.
     */
    boolean owns(String pluginName);

}