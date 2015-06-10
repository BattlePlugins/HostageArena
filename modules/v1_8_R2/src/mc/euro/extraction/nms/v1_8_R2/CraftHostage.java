package mc.euro.extraction.nms.v1_8_R2;

import java.lang.reflect.Field;
import java.util.UUID;
import mc.euro.extraction.nms.Hostage;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityAgeable;
import net.minecraft.server.v1_8_R2.EntityOwnable;
import net.minecraft.server.v1_8_R2.EntityVillager;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

/**
 *
 * @author Nikolai
 */
public class CraftHostage extends EntityVillager implements EntityOwnable, Hostage {
    
    private String owner;
    private String lastOwner;
    
    public CraftHostage(World w) {
        super(w);
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    public CraftHostage(World w, int profession) {
        super(w, profession);
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    private void clearPathfinders() {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    @Override
    public void stay() {
        this.lastOwner = owner;
        this.owner = null;
    }
    
    @Override
    public boolean isStopped() {
        return this.owner == null;
    }
    
    @Override
    public boolean isFollowing() {
        return this.owner != null;
    }
    
    @Override
    public void follow(Player p) {
        follow(p.getName());
    }
    
    @Override
    public void follow(String p) {
        this.owner = p;
    }
    
    @Override
    public void setOwner(Player p) {
        setOwner(p.getName());
    }
    
    @Override
    public void setOwner(String name) {
        this.owner = name;
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Entity getOwner() {
        if (this.owner == null) return null;
        Player player = (Player) Bukkit.getPlayer(this.owner);
        Entity E = ((CraftPlayer) player).getHandle();
        return E;
    }
    
    @Override
    public String getOwnerUUID() {
        if (this.owner == null) {
            return null;
        }
        UUID uuid = getOwner().getUniqueID();
        return uuid.toString();
    }
    
    @Override
    public Location getLocation() {
        Location loc = new Location(world.getWorld(), locX, locY, locZ, yaw, pitch);
        return loc;
    }
    
    @Override
    public void setLocation(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float newYaw = loc.getYaw();
        float newPitch = loc.getPitch();
        setLocation(x, y, z, newYaw, newPitch);
    }

    @Override
    public void removeEntity() {
        world.removeEntity(this);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ea) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Profession getProfessionType() {
        int id = getProfession();
        return Profession.getProfession(id);
    }

    @Override
    public void setProfessionType(Profession x) {
        setProfession(x.getId());
    }

    @Override
    public void setHealth(double health) {
        setHealth((float) health);
    }
    
    @Override
    public Player getRescuer() {
        String name = (owner == null) ? lastOwner : owner;
        Player rescuer = Bukkit.getPlayer(name);
        return rescuer;
    }
    
}
