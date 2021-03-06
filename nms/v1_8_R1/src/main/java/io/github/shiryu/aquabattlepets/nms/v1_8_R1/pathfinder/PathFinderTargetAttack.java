package io.github.shiryu.aquabattlepets.nms.v1_8_R1.pathfinder;

import net.minecraft.server.v1_8_R1.*;
import org.jetbrains.annotations.NotNull;

public class PathFinderTargetAttack extends PathfinderGoal {

    private final EntityInsentient b;
    private final double d;
    private final boolean e;

    private int c,h;
    private double i,j,k;

    public PathFinderTargetAttack(@NotNull final EntityInsentient b, final double d, final boolean e) {
        this.b = b;
        this.d = d;
        this.e = e;

        a(3);
    }

    @Override
    public void e() {
        final EntityLiving localEntityLiving = this.b.getGoalTarget();

        if (localEntityLiving == null) return;

        this.b.getControllerLook().a(localEntityLiving, 30.0F, 30.0F);
        double d1 = this.b.e(localEntityLiving.locX, localEntityLiving.getBoundingBox().b, localEntityLiving.locZ);
        double d2 = a(localEntityLiving);

        this.h -= 1;

        if (((this.e) || (this.b.getEntitySenses().a(localEntityLiving))) &&
                (this.h <= 0) && (
                ((this.i == 0.0D) && (this.j == 0.0D) && (this.k == 0.0D)) || (localEntityLiving.e(this.i, this.j, this.k) >= 1.0D) || (this.b.bb().nextFloat() < 0.05F))) {
            this.i = localEntityLiving.locX;
            this.j = localEntityLiving.getBoundingBox().b;
            this.k = localEntityLiving.locZ;
            this.h = (4 + this.b.bb().nextInt(7));
            if (d1 > 1024.0D) {
                this.h += 10;
            } else if (d1 > 256.0D) {
                this.h += 5;
            }
            b.a(localEntityLiving, 30F, 30F);
            if (!this.b.getNavigation().a(localEntityLiving, this.d)) {
                this.h += 15;
            }
        }
        this.c = Math.max(this.c - 1, 0);
        if ((d1 <= d2) && (this.c <= 0)) {
            this.c = 20;
            if (this.b.bz() != null) {
                this.b.bv();
            }
            this.b.getGoalTarget().damageEntity(DamageSource.mobAttack(b), (float) b.getAttributeInstance(GenericAttributes.e).getValue());
        }
        b.a(localEntityLiving, 30F, 30F);
    }

    protected double a(EntityLiving paramEntityLiving) {
        return this.b.width * 2.0F * (this.b.width * 2.0F) + paramEntityLiving.width;
    }

    @Override
    public boolean a() {
        if (b.passenger != null)
            return false;
        if (b.getGoalTarget() == null)
            return false;
        if (b.getGoalTarget().dead)
            return false;
        return true;
    }
}
