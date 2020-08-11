package io.github.shiryu.aquabattlepets.nms.v1_8_R3;

import io.github.shiryu.aquabattlepets.api.BattlePetMeta;
import io.github.shiryu.aquabattlepets.api.BattlePetType;
import io.github.shiryu.aquabattlepets.api.manager.ManagerHandler;
import io.github.shiryu.aquabattlepets.api.manager.impl.TypeManager;
import io.github.shiryu.aquabattlepets.api.nms.NMSAdapter;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class NMSAdapter_v1_8_R3 implements NMSAdapter {

    @Override
    public @NotNull LivingEntity createPet(@NotNull final BattlePetType type, @NotNull final World world, @NotNull final Location location, @NotNull final Plugin plugin) {
        return (LivingEntity) EntityTypes.spawn(
                EntityTypes.create(
                        type.getName(),
                        ((CraftWorld) world).getHandle()
                ),
                location.clone().add(0, 1, 0)
        ).getBukkitEntity();
    }

    @Override
    public void updatePet(@NotNull final LivingEntity pet, @NotNull final Plugin plugin) {
        final EntityLiving living = ((CraftLivingEntity)pet).getHandle();
        final String type = pet.getMetadata("Type").get(0).asString().toLowerCase();

        String typeconf = "";
        if (type.contains("baby"))
            typeconf += "baby-";

        typeconf += pet.getType().toString().toLowerCase();

        final BattlePetType battlePetType = ManagerHandler
                .getInstance()
                .getManager(TypeManager.class)
                .findType(typeconf)
                .get();

        final BattlePetMeta meta = battlePetType.getMeta();

        living.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(meta.getDamage() + pet.getMetadata("Strength").get(0).asInt());

        pet.setMaxHealth(meta.getHp() + pet.getMetadata("Vitality").get(0).asInt());

        pet.setMetadata("Speed", new FixedMetadataValue(plugin, meta.getSpeed() + pet.getMetadata("Dexterity").get(0).asInt()));
        pet.setMetadata("Damage", new FixedMetadataValue(plugin, meta.getDamage() + pet.getMetadata("Strength").get(0).asInt()));
        pet.setMetadata("XPForLevel", new FixedMetadataValue(plugin, meta.getXpForLevel()  * pet.getMetadata("Level").get(0).asInt()));
    }

    @Override
    public void setTarget(@NotNull final LivingEntity pet, @NotNull final LivingEntity target) {
        if (target instanceof Player) return;

        final EntityInsentient creature = (EntityInsentient) ((CraftLivingEntity)pet).getHandle();

        if (target == null) {
            creature.setGoalTarget(null);
            return;
        }

        creature.setGoalTarget(((CraftLivingEntity) target).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
    }
}
