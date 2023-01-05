package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.DelegateHook;
import com.github.polyrocketmatt.delegate.api.IHandler;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.configuration.DelegateConfiguration;
import com.github.polyrocketmatt.delegate.core.handlers.AnnotationHandler;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;

public class DelegateCore implements DelegateAPI {

    private static final DelegateCore instance = new DelegateCore();

    private DelegateHook hook;
    private IPlatform platform;
    private final DelegateConfiguration configuration;
    private final AnnotationHandler annotationHandler;
    private final AttributeHandler attributeHandler;
    private final CommandHandler commandHandler;

    protected DelegateCore() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.configuration = new DelegateConfiguration();
        this.annotationHandler = new AnnotationHandler();
        this.attributeHandler = new AttributeHandler();
        this.commandHandler = new CommandHandler();
    }

    public void hook(DelegateHook hook) {
        if (this.hook != null)
            throw new IllegalStateException("Delegate was already hooked");
        this.hook = hook;
    }

    public DelegateHook getHook() {
        return hook;
    }

    public static DelegateAPI getDelegateAPI() {
        return instance;
    }

    public static DelegateCore getDelegate() {
        return instance;
    }

    public void setPlatform(IPlatform platform) {
        System.out.println("DELEGATE -> Setting platform to " + platform.getPlatformType().name());

        if (this.platform != null)
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

    public IHandler getAttributeHandler() {
        return this.attributeHandler;
    }

    public IHandler getCommandHandler() {
        return this.commandHandler;
    }

}
