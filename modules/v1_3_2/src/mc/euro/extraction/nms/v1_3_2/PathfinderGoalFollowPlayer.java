package mc.euro.extraction.nms.v1_3_2;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Navigation;
import net.minecraft.server.PathfinderGoal;
import net.minecraft.server.World;

/**
 * Modified PathfinderGoalFollowOwner <br/><br/>
 *
 * @author Nikolai
 */
public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private CraftHostage d;
    private EntityLiving e;
    World a;
    private float f;
    private Navigation g;
    private int h;
    float b;
    float c;
    private boolean i;

    public PathfinderGoalFollowPlayer(CraftHostage paramEntityTameableAnimal, float paramFloat1, float paramFloat2, float paramFloat3) {
        this.d = paramEntityTameableAnimal;
        this.a = paramEntityTameableAnimal.world;
        this.f = paramFloat1;
        this.g = paramEntityTameableAnimal.getNavigation();
        this.c = paramFloat2;
        this.b = paramFloat3;
        a(3);
    }

    @Override
    public boolean a() {
        EntityLiving localEntityLiving = (EntityLiving) this.d.getOwner();
        if (localEntityLiving == null) {
            return false;
        }
        if (this.d.e(localEntityLiving) < this.c * this.c) {
            return false;
        }
        this.e = localEntityLiving;
        return true;
    }

    @Override
    public boolean b() {
        return (!this.g.f()) && (this.d.e(this.e) > this.b * this.b);
    }

    public void e() {
        this.h = 0;
        this.i = this.d.getNavigation().a();
        this.d.getNavigation().a(false);
    }

    @Override
    public void c() {
        this.e = null;
        this.g.g();
        this.d.getNavigation().a(this.i);
    }

    @Override
    public void d() {
        this.d.getControllerLook().a(this.e, 10.0F, this.d.bf());
        if (--this.h > 0) {
            return;
        }
        this.h = 10;
        if (this.g.a(this.e, this.f)) {
            return;
        }
        if (this.d.e(this.e) < 144.0D) {
            return;
        }
        int j = MathHelper.floor(this.e.locX) - 2;
        int k = MathHelper.floor(this.e.locZ) - 2;
        int m = MathHelper.floor(this.e.boundingBox.b);
        for (int n = 0; n <= 4; n++) {
            for (int i1 = 0; i1 <= 4; i1++) {
                if ((n < 1) || (i1 < 1) || (n > 3) || (i1 > 3)) {
                    if ((this.a.t(j + n, m - 1, k + i1)) && (!this.a.s(j + n, m, k + i1)) && (!this.a.s(j + n, m + 1, k + i1))) {
                        this.d.setPositionRotation(j + n + 0.5F, m, k + i1 + 0.5F, this.d.yaw, this.d.pitch);
                        this.g.g();
                        return;
                    }
                }
            }
        }
    }
}
