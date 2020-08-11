package io.github.shiryu.aquabattlepets.nms.v1_9_R1.pet;

import io.github.shiryu.aquabattlepets.api.util.ReflectionUtil;
import io.github.shiryu.aquabattlepets.nms.v1_9_R1.navigation.FlyingNavigation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_9_R1.BossBattleServer;
import net.minecraft.server.v1_9_R1.EntityWither;
import net.minecraft.server.v1_9_R1.World;
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

        this.navigation = new FlyingNavigation(this, this.world);

        init();
    }

    private void init(){
        baby = false;

        BossBattleServer bossbar = ReflectionUtil.getField(
                ReflectionUtil.findPrivateField(
                        EntityWither.class,
                        "bE"
                ),
                this
        );

        bossbar.setVisible(false);

    }

    @Override
    protected void M() {

        if (baby)
            this.l(600);

        if (this.getBukkitEntity().getLocation().getBlock().getLocation().add(0, -1, 0).getBlock().getType().isSolid())
            this.motY += 0.2f;
    }
}
