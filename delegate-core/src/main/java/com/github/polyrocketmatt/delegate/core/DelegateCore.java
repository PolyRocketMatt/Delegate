// Copyright (c) Matthias Kovacic. All rights reserved.
// Licensed under the MIT license.

package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.api.configuration.DelegateConfiguration;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.core.command.tree.CommandNode;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.BrigadierCommandHandler;
import com.github.polyrocketmatt.delegate.core.handlers.DelegateCommandHandler;
import com.github.polyrocketmatt.delegate.core.handlers.InternalCommandHandler;

public class DelegateCore implements DelegateAPI {

    private static final DelegateCore instance = new DelegateCore();

    private IPlatform platform;
    private final DelegateConfiguration configuration;
    private final AttributeHandler attributeHandler;
    private DelegateCommandHandler delegateCommandHandler;
    private final BrigadierCommandHandler brigadierCommandHandler;
    private final InternalCommandHandler internalCommandHandler;
    private boolean isVerbose = false;
    private boolean isBrigadier = false;

    protected DelegateCore() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.configuration = new DelegateConfiguration();
        this.attributeHandler = new AttributeHandler();
        this.brigadierCommandHandler = new BrigadierCommandHandler();
        this.internalCommandHandler = new InternalCommandHandler();
        this.delegateCommandHandler = internalCommandHandler;
    }

    public void setVerbose(boolean verbose) {
        this.isVerbose = verbose;
    }

    public void setBrigadier(boolean brigadier) {
        this.isBrigadier = brigadier;
        if (isBrigadier)
            this.delegateCommandHandler = brigadierCommandHandler;
    }

    public static DelegateAPI getDelegateAPI() {
        return instance;
    }

    public static DelegateCore getDelegate() {
        return instance;
    }

    public void setPlatform(IPlatform platform) {
        if (this.platform != null && platform != null)
            throw new IllegalStateException("Platform has already been set and cannot be changed at runtime");
        this.platform = platform;
    }

    @Override
    public IPlatform getPlatform() {
        return this.platform;
    }

    @Override
    public DelegateConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public AttributeHandler getAttributeHandler() {
        return this.attributeHandler;
    }

    @Override
    public DelegateCommandHandler getCommandHandler() {
        return this.delegateCommandHandler;
    }

    @Override
    public boolean registerCommand(ICommandNode node) throws CommandRegisterException {
        if (!(node instanceof CommandNode commandNode))
            throw new CommandRegisterException("Node must be an instance of CommandNode");
        boolean success;

        success = brigadierCommandHandler.registerCommand(commandNode);
        success = internalCommandHandler.registerCommand(commandNode);

        return success;
    }

    @Override
    public boolean isVerbose() {
        return isVerbose;
    }

    @Override
    public boolean useBrigadier() {
        return isBrigadier;
    }

    public InternalCommandHandler getInternalCommandHandler() {
        return internalCommandHandler;
    }

    public BrigadierCommandHandler getBrigadierCommandHandler() {
        return brigadierCommandHandler;
    }
}
