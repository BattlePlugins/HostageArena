package mc.euro.extraction.nms.v1_4_R1;

import java.lang.reflect.Field;
import mc.euro.extraction.nms.Hostage;
import net.minecraft.server.v1_4_R1.Entity;
import net.minecraft.server.v1_4_R1.EntityAgeable;
import net.minecraft.server.v1_4_R1.EntityVillager;
import net.minecraft.server.v1_4_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_4_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftVillager;
import org.bukkit.craftbukkit.v1_4_R1.util.UnsafeList;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

/**
 *
 * @author Nikolai
 */
public class CraftHostage extends EntityVillager implements Hostage {
    
    private String owner;
    private String lastOwner;
    private String customName;
    
    public CraftHostage(World w) {
        super(w);
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0F, 2.0F, 2.0F));
        ((CraftVillager) super.getBukkitEntity()).setMaxHealth(20);
        this.setHealth(20);
    }
    
    public CraftHostage(World w, int profession) {
        super(w, profession);
        clearPathfinders();
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0F, 2.0F, 2.0F));
        ((CraftVillager) super.getBukkitEntity()).setMaxHealth(20);
        this.setHealth(20);
    }
    
    private void clearPathfinders() {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("a");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("b");
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
        this.owner = p.getName();
    }
    
    @Override
    public void setOwner(String name) {
        this.owner = name;
    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }
    
    public Entity getOwner() {
        if (this.owner == null) return null;
        Player player = (Player) Bukkit.getPlayer(this.owner);
        int ownerID = player.getEntityId();
        Entity E = (Entity) this.world.getEntity(ownerID);
        return E;
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
        setHealth((int) health);
    }

    @Override
    public Player getRescuer() {
        String name = (owner == null) ? lastOwner : owner;
        if (name == null) return null;
        Player rescuer = Bukkit.getPlayer(name);
        return rescuer;
    }

    public String getCustomName() {
        return this.customName;
    }

    @Override
    public void setCustomName(String name) {
        this.customName = name;
    }

}
