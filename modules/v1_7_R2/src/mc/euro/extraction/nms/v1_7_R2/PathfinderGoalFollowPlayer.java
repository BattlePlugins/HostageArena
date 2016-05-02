package mc.euro.extraction.nms.v1_7_R2;

import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.MathHelper;
import net.minecraft.server.v1_7_R2.Navigation;
import net.minecraft.server.v1_7_R2.PathfinderGoal;
import net.minecraft.server.v1_7_R2.World;

/**
 * Modified PathfinderGoalFollowOwner <br/><br/>
 *
 * @author Nikolai
 */
public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private CraftHostage d;
    private EntityLiving e;
    World a;
    private double f;
    private Navigation g;
    private int h;
    float b;
    float c;
    private boolean i;

    public PathfinderGoalFollowPlayer(CraftHostage paramEntityTameableAnimal, double paramDouble, float paramFloat1, float paramFloat2) {
        this.d = paramEntityTameableAnimal;
        this.a = paramEntityTameableAnimal.world;
        this.f = paramDouble;
        this.g = paramEntityTameableAnimal.getNavigation();
        this.c = paramFloat1;
        this.b = paramFloat2;
        a(3);
    }

    public boolean a() {
        EntityLiving localEntityLiving = (EntityLiving) this.d.getOwner();
        if (localEntityLiving == null) {
            return false;
        }
        if (this.d.f(localEntityLiving) < this.c * this.c) {
            return false;
        }
        this.e = localEntityLiving;
        return true;
    }

    public boolean b() {
        return (!this.g.g()) && (this.d.f(this.e) > this.b * this.b);
    }

    public void c() {
        this.h = 0;
        this.i = this.d.getNavigation().a();
        this.d.getNavigation().a(false);
    }

    public void d() {
        this.e = null;
        this.g.h();
        this.d.getNavigation().a(this.i);
    }

    public void e() {
        this.d.getControllerLook().a(this.e, 10.0F, this.d.bv());
        if (--this.h > 0) {
            return;
        }
        this.h = 10;
        if (this.g.a(this.e, this.f)) {
            return;
        }
        if (this.d.bN()) {
            return;
        }
        if (this.d.f(this.e) < 144.0D) {
            return;
        }
        int j = MathHelper.floor(this.e.locX) - 2;
        int k = MathHelper.floor(this.e.locZ) - 2;
        int m = MathHelper.floor(this.e.boundingBox.b);
        for (int n = 0; n <= 4; n++) {
            for (int i1 = 0; i1 <= 4; i1++) {
                if ((n < 1) || (i1 < 1) || (n > 3) || (i1 > 3)) {
                    if ((World.a(this.a, j + n, m - 1, k + i1)) && (!this.a.getType(j + n, m, k + i1).r()) && (!this.a.getType(j + n, m + 1, k + i1).r())) {
                        this.d.setPositionRotation(j + n + 0.5F, m, k + i1 + 0.5F, this.d.yaw, this.d.pitch);
                        this.g.h();
                        return;
                    }
                }
            }
        }
    }
}
