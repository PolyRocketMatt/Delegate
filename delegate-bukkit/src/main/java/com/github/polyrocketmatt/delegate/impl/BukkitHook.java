package com.github.polyrocketmatt.delegate.impl;

import com.github.polyrocketmatt.delegate.api.DelegateHook;
import org.bukkit.plugin.Plugin;

public record BukkitHook(Plugin plugin)  implements DelegateHook {}
