package mc.euro.extraction.nms.v1_13_R1;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.EntityHuman;
import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.IBlockData;
import net.minecraft.server.v1_13_R1.Material;
import net.minecraft.server.v1_13_R1.MathHelper;
import net.minecraft.server.v1_13_R1.Navigation;
import net.minecraft.server.v1_13_R1.NavigationAbstract;
import net.minecraft.server.v1_13_R1.PathType;
import net.minecraft.server.v1_13_R1.PathfinderGoal;
import net.minecraft.server.v1_13_R1.World;

/**
 * Modified PathfinderGoalFollowOwner. <br/><br/>
 *
 * @author Nikolai
 */
public class PathfinderGoalFollowPlayer extends PathfinderGoal {

    private final CraftHostage d;
    private EntityLiving e;
    World a;
    private final double f;
    private final NavigationAbstract g;
    private int h;
    float b;
    float c;
    private float i;

    public PathfinderGoalFollowPlayer(CraftHostage paramEntityTameableAnimal, double paramDouble, float paramFloat1, float paramFloat2) {
        this.d = paramEntityTameableAnimal;
        this.a = paramEntityTameableAnimal.world;
        this.f = paramDouble;
        this.g = paramEntityTameableAnimal.getNavigation();
        this.c = paramFloat1;
        this.b = paramFloat2;
        a(3);
        if (!(paramEntityTameableAnimal.getNavigation() instanceof Navigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean a() {
        if (!(this.d.getOwner() instanceof EntityLiving)) {
            return false;
        }
        EntityLiving localEntityLiving = (EntityLiving) this.d.getOwner();
        if (localEntityLiving == null) {
            return false;
        }
        if (((localEntityLiving instanceof EntityHuman)) && (((EntityHuman) localEntityLiving).isSpectator())) {
            return false;
        }
        if (this.d.h(localEntityLiving) < this.c * this.c) {
            return false;
        }
        this.e = localEntityLiving;
        return true;
    }

    @Override
    public boolean b() {
        return (!this.g.p()) && (this.d.h(this.e) > this.b * this.b);
    }

    public void c() {
        this.h = 0;
        this.i = this.d.a(PathType.WATER);
        this.d.a(PathType.WATER, 0.0F);
    }

    @Override
    public void d() {
        this.e = null;
        this.g.p();
        this.d.a(PathType.WATER, this.i);
    }

    private boolean a(BlockPosition paramBlockPosition) {
        IBlockData localIBlockData = this.a.getType(paramBlockPosition);
        if (localIBlockData.getMaterial() == Material.AIR) {
            return true;
        }
        return !localIBlockData.g();
    }

    @Override
    public void e() {
        this.d.getControllerLook().a(this.e, 10.0F, this.d.K());
        if (--this.h > 0) {
            return;
        }
        this.h = 10;
        if (this.g.a(this.e, this.f)) {
            return;
        }
        if (this.d.isLeashed()) {
            return;
        }
        if (this.d.h(this.e) < 144.0D) {
            return;
        }
        int j = MathHelper.floor(this.e.locX) - 2;
        int k = MathHelper.floor(this.e.locZ) - 2;
        int m = MathHelper.floor(this.e.getBoundingBox().b);
        for (int n = 0; n <= 4; n++) {
            for (int i1 = 0; i1 <= 4; i1++) {
                if ((n < 1) || (i1 < 1) || (n > 3) || (i1 > 3)) {
                    if ((this.a.getType(new BlockPosition(j + n, m - 1, k + i1)).q()) && (a(new BlockPosition(j + n, m, k + i1))) && (a(new BlockPosition(j + n, m + 1, k + i1)))) {
                        this.d.setPositionRotation(j + n + 0.5F, m, k + i1 + 0.5F, this.d.yaw, this.d.pitch);
                        this.g.p();
                        return;
                    }
                }
            }
        }
    }
}
