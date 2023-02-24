// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.api;

import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.api.configuration.DelegateConfiguration;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.api.handlers.CommandHandler;
import com.github.polyrocketmatt.delegate.api.handlers.IHandler;
import org.apiguardian.api.API;

/**
 * The Delegate API which is the entry point for platform implementations
 * to access the Delegate command framework.
 *
 * @since 0.0.1
 * @author Matthias Kovacic
 */
@API(status = API.Status.STABLE, since = "0.0.1")
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
    CommandHandler getCommandHandler();

    /**
     * Register a command node to the Delegate API.
     *
     * @param node The command node to register.
     * @return Whether the command node was successfully registered.
     */
    boolean registerCommand(ICommandNode node) throws CommandRegisterException;

    /**
     * Gets whether the Delegate API is in verbose mode and will throw exceptions.
     *
     * @return Whether the Delegate API is in verbose mode and will throw exceptions.
     */
    boolean isVerbose();

    /**
     * Gets whether the Delegate API is using the Brigadier command framework.
     *
     * @return Whether the Delegate API is using the Brigadier command framework.
     */
    boolean useBrigadier();

}
