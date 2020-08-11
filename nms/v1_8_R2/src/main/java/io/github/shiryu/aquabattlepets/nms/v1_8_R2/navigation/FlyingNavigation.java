package io.github.shiryu.aquabattlepets.nms.v1_8_R2.navigation;

import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.Navigation;
import net.minecraft.server.v1_8_R2.World;
import org.jetbrains.annotations.NotNull;

public class FlyingNavigation extends Navigation {

    public FlyingNavigation(@NotNull final EntityInsentient entityInsentient, @NotNull final World world) {
        super(entityInsentient, world);
    }

    @Override
    protected boolean b() {
        return true;
    }
}
