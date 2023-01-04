package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegistrationException;
import com.github.polyrocketmatt.delegate.api.exception.DelegateRuntimeException;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.impl.command.BukkitCommandFactory;
import com.github.polyrocketmatt.delegate.impl.entity.BukkitPlayerCommander;
import com.github.polyrocketmatt.delegate.impl.event.DelegateCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Delegate implements IPlatform, CommandExecutor {

    private static final BukkitCommandFactory factory = new BukkitCommandFactory();
    private final CommandMap commandMap;

    protected Delegate() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            this.commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch(Exception ex) {
            throw new DelegateRuntimeException("Unable to retrieve command map", ex);
        }
    }

    public static void hook(Plugin plugin) {
        DelegateCore.getDelegate().setPlatform(new Delegate());
        DelegateCore.getDelegate().hook(new BukkitHook(plugin));
    }

    public static DelegateAPI getDelegateAPI() {
        return DelegateCore.getDelegateAPI();
    }

    public static BukkitCommandFactory getFactory() {
        return factory;
    }

    private Plugin getPlugin() {
        if (DelegateCore.getDelegate().getHook() == null)
            throw new CommandRegistrationException("Plugin is not hooked into Delegate!");
        return ((BukkitHook) DelegateCore.getDelegate().getHook()).plugin();
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUKKIT;
    }

    @Override
    public ICommandFactory getFactoryImplementation() {
        return factory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void register(IDelegateCommand command) throws CommandRegistrationException {
        if (this.getPlugin() == null)
            throw new CommandRegistrationException("Plugin is not hooked into Delegate!");

        try {
            Constructor<PluginCommand> commandConstructor = (Constructor<PluginCommand>) PluginCommand.class.getDeclaredConstructors()[0];
            commandConstructor.setAccessible(true);
            PluginCommand cmd = commandConstructor.newInstance(command.getNameDefinition().getValue(), this.getPlugin());

            //  Register command to the command map
            commandMap.register(command.getNameDefinition().getValue(), cmd);

            //  Set the command executor
            cmd.setExecutor(this);

            //  Setting description
            cmd.setDescription(command.getDescriptionDefinition().getValue());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new CommandRegistrationException("Unable to register command: %s".formatted(command.getNameDefinition().getValue()), ex);
        }
    }

    @Override
    public boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException {
        if (!(entity instanceof BukkitPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.hasPermission(permission);
    }

    @Override
    public boolean dispatch(CommandDispatchInformation information, CommandCapture capture) {
        Bukkit.getServer().getPluginManager().callEvent(new DelegateCommandEvent(information, capture));

        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }
}
