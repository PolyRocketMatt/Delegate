package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.tree.ICommandTree;
import com.github.polyrocketmatt.delegate.api.configuration.DelegateConfiguration;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.BrigadierCommandHandler;
import com.github.polyrocketmatt.delegate.core.handlers.DelegateCommandHandler;

public class DelegateCore implements DelegateAPI {

    private static final DelegateCore instance = new DelegateCore();

    private IPlatform platform;
    private final DelegateConfiguration configuration;
    private final AttributeHandler attributeHandler;
    private final BrigadierCommandHandler brigadierCommandHandler;
    private final DelegateCommandHandler commandHandler;
    private boolean isVerbose = false;
    private boolean isBrigadier = false;

    protected DelegateCore() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.configuration = new DelegateConfiguration();
        this.attributeHandler = new AttributeHandler();
        this.brigadierCommandHandler = new BrigadierCommandHandler();
        this.commandHandler = new DelegateCommandHandler();
    }

    public void setVerbose(boolean verbose) {
        this.isVerbose = verbose;
    }

    public void setBrigadier(boolean brigadier) {
        this.isBrigadier = brigadier;
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
    public BrigadierCommandHandler getBrigadierCommandHandler() {
        return brigadierCommandHandler;
    }

    @Override
    public DelegateCommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public ICommandTree getCommandTree() {
        return this.commandHandler.getCommandTree();
    }

    @Override
    public boolean verbose() {
        return isVerbose;
    }

    @Override
    public boolean brigadier() {
        return isBrigadier;
    }
}
