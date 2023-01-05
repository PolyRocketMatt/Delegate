package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.configuration.DelegateConfiguration;

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
     * Gets the {@link DelegateConfiguration} configuration that is used by the Delegate API.
     *
     * @return The configuration that is used by the Delegate API.
     */
    DelegateConfiguration getConfiguration();

}
