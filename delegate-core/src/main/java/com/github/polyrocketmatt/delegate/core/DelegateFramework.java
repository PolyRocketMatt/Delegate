package com.github.polyrocketmatt.delegate.core;

import com.github.polyrocketmatt.delegate.core.handlers.AnnotationHandler;
import com.github.polyrocketmatt.delegate.core.handlers.AttributeHandler;
import com.github.polyrocketmatt.delegate.core.handlers.CommandHandler;
import com.github.polyrocketmatt.delegate.core.platform.Platform;

public class DelegateFramework {

    private static final DelegateFramework instance = new DelegateFramework();

    private Platform platform;
    private final AnnotationHandler annotationHandler;
    private final AttributeHandler attributeHandler;
    private final CommandHandler commandHandler;

    protected DelegateFramework() {
        if (instance != null)
            throw new IllegalStateException("Delegate has already been initialized");
        this.annotationHandler = new AnnotationHandler();
        this.attributeHandler = new AttributeHandler();
        this.commandHandler = new CommandHandler();
    }

    public static DelegateFramework getDelegate() {
        return instance;
    }

    public void setPlatform(Platform platform) {
        if (this.platform != null)
            throw new IllegalStateException("Platform has already been set and cannot be changed at runtime");
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public AnnotationHandler getAnnotationHandler() {
        return annotationHandler;
    }

    public AttributeHandler getAttributeHandler() {
        return attributeHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public String getDelegateVersion() {
        if (this.platform == null)
            return null;
        return this.platform.getPluginVersion();
    }

}
