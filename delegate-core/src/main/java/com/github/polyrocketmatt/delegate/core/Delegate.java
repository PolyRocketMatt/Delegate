package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.core.command.CommandFactory;
import com.github.polyrocketmatt.delegate.core.handlers.AnnotationHandler;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;

public class Delegate implements DelegateAPI {

    private static final Delegate instance = new Delegate();

    private IPlatform platform;
    private final AnnotationHandler annotationHandler;
    private final AttributeHandler attributeHandler;
    private final CommandHandler commandHandler;
    private final CommandFactory commandFactory;

    protected Delegate() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.annotationHandler = new AnnotationHandler();
        this.attributeHandler = new AttributeHandler();
        this.commandHandler = new CommandHandler();
        this.commandFactory = new CommandFactory();
    }

    public static DelegateAPI getDelegateAPI() {
        return instance;
    }

    public static Delegate getDelegate() {
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
    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public IHandler getAttributeHandler() {
        return attributeHandler;
    }

    public IHandler getCommandHandler() {
        return commandHandler;
    }

    public String getDelegateVersion() {
        if (this.platform == null)
            return null;
        return this.platform.getPluginVersion();
    }

}
