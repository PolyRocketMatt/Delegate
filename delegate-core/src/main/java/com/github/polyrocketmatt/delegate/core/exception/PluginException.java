package com.github.polyrocketmatt.delegate.core.exception;

import java.io.IOException;

/**
 * @author Matthias Kovacic
 * @since 0.0.1
 *
 * Exception that is thrown when something goes wrong for a certain plugin.
 */
public class PluginException extends IOException {

    /**
     * Create a new PluginException.
     */
    public PluginException() {
        super();
    }

    /**
     * Create a new PluginException with the given message.
     *
     * @param message The message to use.
     */
    public PluginException(String message) {
        super(message);
    }

    /**
     * Create a new PluginException with the given message and cause.
     *
     * @param message The message to use.
     * @param cause The cause to use.
     */
    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new PluginException with the given cause.
     *
     * @param cause The cause to use.
     */
    public PluginException(Throwable cause) {
        super(cause);
    }

}