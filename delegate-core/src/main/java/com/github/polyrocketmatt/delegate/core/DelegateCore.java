package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.core.handlers.AnnotationHandler;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;

public class DelegateCore implements DelegateAPI {

    private static final DelegateCore instance = new DelegateCore();

    private IPlatform platform;
    private final AnnotationHandler annotationHandler;
    private final AttributeHandler attributeHandler;
    private final CommandHandler commandHandler;

    protected DelegateCore() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.annotationHandler = new AnnotationHandler();
        this.attributeHandler = new AttributeHandler();
        this.commandHandler = new CommandHandler();
    }

    public static DelegateAPI getDelegateAPI() {
        return instance;
    }

    public static DelegateCore getDelegate() {
        return instance;
    }

    public void setPlatform(IPlatform platform) {
        if (this.platform != null)
            throw new IllegalStateException("Platform has already been set and cannot be changed at runtime");
        this.platform = platform;
    }

    @Override
    public IPlatform getPlatform() {
        return platform;
    }

    @Override
    public ICommandFactory getCommandFactory() {
        return null;
    }

    public IHandler getAttributeHandler() {
        return attributeHandler;
    }

    public IHandler getCommandHandler() {
        return commandHandler;
    }

}
