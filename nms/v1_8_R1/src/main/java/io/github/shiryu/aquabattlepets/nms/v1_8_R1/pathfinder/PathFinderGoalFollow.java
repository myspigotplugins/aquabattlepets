package io.github.shiryu.aquabattlepets.nms.v1_8_R1.pathfinder;

import io.github.shiryu.aquabattlepets.api.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;

@RequiredArgsConstructor
public class PathFinderGoalFollow extends PathfinderGoal {

    private final EntityPlayer player;
    private final EntityInsentient creature;

    private final double range,tpRange;
    private final double speed;

    @Override
    public boolean a() {
        if (creature.passenger != null){
            riding();

            return false;
        }

        if (creature.getWorld().getWorld().getName() != player.world.getWorld().getName()){
            creature.setGoalTarget(null);

            tp();
            return false;
        }

        final double dist = creature.getBukkitEntity().getLocation().distance(player.getBukkitEntity().getLocation());

        if (dist >= tpRange){
            creature.setGoalTarget(null);

            tp();

            return false;
        }

        if (dist >= range){
            if (creature.getGoalTarget() == null)
                return true;

            if (creature.getGoalTarget().dead){
                creature.setGoalTarget(null);

                return true;
            }

            return false;
        }

        if (creature.getGoalTarget() == null)
            creature.getNavigation().a(player, 0);

        return false;
    }

    private void tp(){
        ((LivingEntity) creature.getBukkitEntity()).teleport(player.getBukkitEntity().getLocation());
    }

    private void riding() {
        if (creature.passenger != player) return;

        creature.lastYaw = (creature.yaw = player.yaw);
        creature.pitch = (player.pitch * 0.5F);
        creature.getBukkitEntity().getLocation().setYaw(creature.yaw);
        creature.getBukkitEntity().getLocation().setPitch(creature.pitch);
        creature.aK = (creature.aI = creature.yaw);

        float f, f1;
        f = player.aX * 0.5F;
        f1 = player.aY;

        if (f1 <= 0.0F)
            f1 *= 0.25F;

        f *= 0.75F;

        creature.j((float) speed * 0.7F);
        creature.g(f, f1);
        creature.S = 1F;

        final Field jump = ReflectionUtil.findPrivateField(EntityLiving.class, "aW");


        if (jump != null && creature.onGround){
            final boolean passenger = ReflectionUtil.getField(
                    jump,
                    creature.passenger
            );

            if (passenger)
                creature.motY = 0.5D;
        }
    }
}
