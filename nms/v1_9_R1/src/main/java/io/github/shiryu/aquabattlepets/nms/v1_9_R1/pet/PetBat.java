package io.github.shiryu.aquabattlepets.nms.v1_9_R1.pet;

import io.github.shiryu.aquabattlepets.nms.v1_9_R1.navigation.FlyingNavigation;
import net.minecraft.server.v1_9_R1.EntityBat;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.World;
import org.jetbrains.annotations.NotNull;

public class PetBat extends EntityBat {

    public PetBat(@NotNull final World world) {
        super(world);
    }

    public PetBat(@NotNull final World arg, final boolean l) {
        super(arg);

        this.navigation = new FlyingNavigation(this, this.world);
        this.setAsleep(false);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5);
    }

    @Override
    protected void M() {
        if (this.getBukkitEntity().getLocation().getBlock().getLocation().add(0, -1, 0).getBlock().getType().isSolid())
            this.motY += 0.2f;
    }
}
