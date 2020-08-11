package io.github.shiryu.aquabattlepets.nms.v1_8_R2.pets;

import io.github.shiryu.aquabattlepets.nms.v1_8_R2.navigation.FlyingNavigation;
import net.minecraft.server.v1_8_R2.EntityBat;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.World;
import org.jetbrains.annotations.NotNull;

public class PetBat extends EntityBat {

    public PetBat(@NotNull final World world) {
        super(world);
    }

    public PetBat(@NotNull final World arg, final boolean l) {
        super(arg);

        this.navigation = new FlyingNavigation(this, this.world);
        this.setAsleep(false);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.5);
    }

    @Override
    protected void E() {
        if (this.getBukkitEntity().getLocation().getBlock().getLocation().add(0, -1, 0).getBlock().getType().isSolid())
            this.motY += 0.2f;
    }

}
