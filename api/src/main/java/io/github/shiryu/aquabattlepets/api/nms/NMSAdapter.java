package io.github.shiryu.aquabattlepets.api.nms;

import io.github.shiryu.aquabattlepets.api.BattlePetType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface NMSAdapter {

    @NotNull
    LivingEntity createPet(@NotNull final BattlePetType type, @NotNull final World world, @NotNull final Location location, @NotNull final Plugin plugin);

    void updatePet(@NotNull final LivingEntity pet, @NotNull final Plugin plugin);

    void setTarget(@NotNull final LivingEntity pet, @NotNull final LivingEntity target);
}
