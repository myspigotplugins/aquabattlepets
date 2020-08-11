package io.github.shiryu.aquabattlepets.nms.v1_8_R1.pets;

import io.github.shiryu.aquabattlepets.nms.v1_8_R1.navigation.FlyingNavigation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R1.EntityWither;
import net.minecraft.server.v1_8_R1.World;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class PetWither extends EntityWither {

    private boolean baby;

    public PetWither(@NotNull final World world) {
        super(world);
    }

    public PetWither(@NotNull final World world, boolean t) {
        super(world);

        baby = false;
        this.navigation = new FlyingNavigation(this, this.world);
    }

    @Override
    protected void E() {
        if (baby)
            this.r(600);
        if (this.getBukkitEntity().getLocation().getBlock().getLocation().add(0, -1, 0).getBlock().getType().isSolid())
            this.motY += 0.2f;
    }
}
