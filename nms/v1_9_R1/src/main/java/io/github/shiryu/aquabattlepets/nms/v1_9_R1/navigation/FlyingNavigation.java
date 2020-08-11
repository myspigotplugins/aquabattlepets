package io.github.shiryu.aquabattlepets.nms.v1_9_R1.navigation;

import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.Navigation;
import net.minecraft.server.v1_9_R1.World;
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
