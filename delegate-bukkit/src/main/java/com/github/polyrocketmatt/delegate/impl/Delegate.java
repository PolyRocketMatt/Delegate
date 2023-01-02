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

import java.lang.reflect.InvocationTargetException;

public class Delegate implements IPlatform, CommandExecutor {

    private static final BukkitCommandFactory factory = new BukkitCommandFactory();

    public Delegate() {}

    public static void hook(Plugin plugin) {
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

    @Override
    public void register(IDelegateCommand command) throws CommandRegistrationException {
        try {
            CommandMap commandMap = Bukkit.getServer().getCommandMap();
            Command cmd = Command.class
                    .getDeclaredConstructor(String.class)
                    .newInstance(command.getNameDefinition().getValue());

            //  Register command to the command map
            commandMap.register(command.getNameDefinition().getValue(), cmd);

            PluginCommand pluginCmd = PluginCommand.class
                    .getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class)
                    .newInstance(command.getNameDefinition().getValue(), this.getPlugin());

            //  Set the command executor
            pluginCmd.setExecutor(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
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
