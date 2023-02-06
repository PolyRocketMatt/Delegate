package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.tree.ICommandTree;
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

    /**
     * Gets the attribute handler that is used by the Delegate API.
     *
     * @return The attribute handler that is used by the Delegate API.
     */
    IHandler getAttributeHandler();

    /**
     * Gets the command handler that is used by the Delegate API.
     *
     * @return The command handler that is used by the Delegate API.
     */
    IHandler getCommandHandler();

    /**
     * Gets the command tree that stores all the commands that are registered.
     *
     * @return The command tree that stores all the commands that are registered.
     */
    ICommandTree getCommandTree();

    /**
     * Gets whether the Delegate API is in verbose mode and will throw exceptions.
     *
     * @return Whether the Delegate API is in verbose mode and will throw exceptions.
     */
    boolean verbose();

}
