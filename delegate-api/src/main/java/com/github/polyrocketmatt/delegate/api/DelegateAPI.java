package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;

/**
 * The Delegate API which is the entry point for platform implementations
 * to access the Delegate command framework.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
public interface DelegateAPI {

    /**
     * Gets the {@link IPlatform} platform that is implemented.
     *
     * @return The platform that is implemented.
     */
    IPlatform getPlatform();

    /**
     * Gets the {@link ICommandFactory} command factory.
     *
     * @return The command factory.
     */
    ICommandFactory getCommandFactory();

}
