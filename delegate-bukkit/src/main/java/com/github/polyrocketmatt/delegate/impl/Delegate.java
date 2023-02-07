package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.command.CommandDispatchInformation;
import com.github.polyrocketmatt.delegate.api.command.ICommandFactory;
import com.github.polyrocketmatt.delegate.api.command.IDelegateCommand;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.api.DelegateAPI;
import com.github.polyrocketmatt.delegate.api.IPlatform;
import com.github.polyrocketmatt.delegate.api.PlatformType;
import com.github.polyrocketmatt.delegate.api.entity.ConsoleCommander;
import com.github.polyrocketmatt.delegate.api.exception.CommandRegisterException;
import com.github.polyrocketmatt.delegate.api.exception.DelegateRuntimeException;
import com.github.polyrocketmatt.delegate.core.DelegateCore;
import com.github.polyrocketmatt.delegate.core.handlers.DelegateCommandHandler;
import com.github.polyrocketmatt.delegate.impl.command.BukkitCommandFactory;
import com.github.polyrocketmatt.delegate.impl.entity.BukkitPlayerCommander;
import com.github.polyrocketmatt.delegate.impl.event.DelegateCommandEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Delegate implements IPlatform, CommandExecutor, TabExecutor {

    private static final int BUKKIT_DELEGATE_ID = 17314;

    private static final BukkitCommandFactory factory = new BukkitCommandFactory();

    private final Plugin plugin;
    private final CommandMap commandMap;
    private final List<IDelegateCommand> commands = new ArrayList<>();
    private final DelegateCommandHandler commandHandler;
    private final boolean metricsEnabled;

    protected Delegate(JavaPlugin plugin, boolean metricsEnabled) {
        this.plugin = plugin;
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            this.commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch(Exception ex) {
            throw new DelegateRuntimeException("Unable to retrieve command map", ex);
        }

        this.commandHandler = (DelegateCommandHandler) DelegateCore.getDelegate().getCommandHandler();
        this.metricsEnabled = metricsEnabled;

        if (metricsEnabled)
            new Metrics(plugin, BUKKIT_DELEGATE_ID);
    }

    public static void hook(JavaPlugin plugin) {
        hook(plugin, true, true);
    }

    public static void hook(JavaPlugin plugin, boolean enableMetrics, boolean verbose) {
        DelegateCore.getDelegate().setPlatform(new Delegate(plugin, enableMetrics));
        DelegateCore.getDelegate().setVerbose(verbose);
    }

    public static void unhook(JavaPlugin plugin) {
        Delegate delegate = (Delegate) DelegateCore.getDelegate().getPlatform();
        delegate.unregister();

        DelegateCore.getDelegate().setPlatform(null);
    }

    public static DelegateAPI getDelegateAPI() {
        return DelegateCore.getDelegateAPI();
    }

    public static BukkitCommandFactory getFactory() {
        return factory;
    }

    private Plugin getPlugin() {
        return plugin;
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
    public void register(IDelegateCommand command) throws CommandRegisterException {
        if (this.getPlugin() == null)
            throw new CommandRegisterException("Plugin is not hooked into Delegate!");
        if (this.commandMap == null)
            throw new CommandRegisterException("Unable to retrieve command map!");
        if (command == null)
            throw new CommandRegisterException("Command is null!");
        if (commands.stream().anyMatch(cmd -> cmd.getNameDefinition().getValue().equalsIgnoreCase(command.getNameDefinition().getValue())))
            throw new CommandRegisterException("Command already registered: %s".formatted(command.getNameDefinition().getValue()));
        commands.add(command);

        try {
            Constructor<PluginCommand> commandConstructor = (Constructor<PluginCommand>) PluginCommand.class.getDeclaredConstructors()[0];
            commandConstructor.setAccessible(true);
            PluginCommand cmd = commandConstructor.newInstance(command.getNameDefinition().getValue(), this.getPlugin());

            //  Register command to the command map
            commandMap.register(command.getNameDefinition().getValue(), cmd);

            //  Set the command/tab executors
            cmd.setExecutor(this);
            cmd.setTabCompleter(this);

            //  Setting description
            cmd.setDescription(command.getDescriptionDefinition().getValue());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new CommandRegisterException("Unable to register command: %s".formatted(command.getNameDefinition().getValue()), ex);
        }
    }

    private void unregister() throws CommandRegisterException {
        commands.clear();
    }

    @Override
    public boolean hasPermission(CommanderEntity entity, String permission) throws UnsupportedOperationException {
        if (!(entity instanceof BukkitPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.hasPermission(permission);
    }

    @Override
    public boolean isOperator(CommanderEntity entity) throws UnsupportedOperationException {
        if (!(entity instanceof BukkitPlayerCommander commander))
            throw new UnsupportedOperationException("Expected entity to be of type BukkitPlayerCommander, but got %s".formatted(entity.getClass().getName()));
        return commander.isOperator();
    }

    @Override
    public boolean dispatch(CommandDispatchInformation information, CommandCapture capture) {
        DelegateCommandEvent event = new DelegateCommandEvent(plugin, information, capture);
        Bukkit.getServer().getPluginManager().callEvent(event);

        return true;
    }

    @Override
    public boolean metricsEnabled() {
        return metricsEnabled;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommanderEntity entity = (sender instanceof Player) ? new BukkitPlayerCommander((Player) sender) : new ConsoleCommander();
        CommandDispatchInformation information = new CommandDispatchInformation(entity, command.getName(), args);

        try {
            return commandHandler.handle(information);
        } catch (Exception ex) {
            if (DelegateCore.getDelegate().verbose())
                ex.printStackTrace();
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String[] newArgs = new String[args.length + 1];
        newArgs[0] = command.getName();
        System.arraycopy(args, 0, newArgs, 1, args.length);

        return commandHandler.findCompletions(newArgs);
    }
}
