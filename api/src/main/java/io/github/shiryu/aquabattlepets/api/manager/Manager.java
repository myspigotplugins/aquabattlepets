package io.github.shiryu.aquabattlepets.api.manager;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface Manager {

    void handle(@NotNull final Plugin plugin);
}
