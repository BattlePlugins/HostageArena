package mc.euro.extraction.nms.v1_14_R1;

import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.util.UUID;

import mc.euro.extraction.nms.Hostage;

import net.minecraft.server.v1_14_R1.ChatComponentText;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EntityVillager;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_14_R1.VillagerType;
import net.minecraft.server.v1_14_R1.World;

import net.minecraft.server.v1_14_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;


/**
 *
 * @author Nikolai
 */
public class CraftHostage extends EntityVillager implements Hostage {
    
    private String owner;
    private String lastOwner;
    
    public CraftHostage(EntityTypes<? extends EntityVillager> entitytypes, World w) {
        super(entitytypes, w);
        clearPathfinders();

        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    public CraftHostage(World w, Profession profession) {
        super(EntityTypes.VILLAGER, w);
        clearPathfinders();
        this.setVillagerData(this.getVillagerData().withType(VillagerType.c).withProfession(VillagerUtils.fromBukkitProfession(profession)));
        this.goalSelector.a(10, new PathfinderGoalFollowPlayer(this, 1.0D, 2.0F, 2.0F));
    }
    
    private void clearPathfinders() {
        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(goalSelector, Sets.newLinkedHashSet());
            bField.set(targetSelector, Sets.newLinkedHashSet());
            cField.set(goalSelector, Sets.newLinkedHashSet());
            cField.set(targetSelector, Sets.newLinkedHashSet());
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

    public Entity getOwner() {
        if (this.owner == null) return null;
        Player player = Bukkit.getPlayer(this.owner);
        EntityPlayer ep = ((CraftPlayer)player).getHandle();
        return ep;
    }

    public UUID getOwnerUUID() {
        if (this.owner == null) {
            return null;
        }
        return getOwner().getUniqueID();
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
        ((WorldServer) world).removeEntity(this);
    }

    @Override
    public Profession getProfessionType() {
        return getProfessionType();
    }

    @Override
    public void setProfessionType(Profession x) {
        setVillagerData(getVillagerData().withType(VillagerType.c).withProfession(VillagerUtils.fromBukkitProfession(x)));
    }

    @Override
    public void setCustomName(String name) {
        super.setCustomName(new ChatComponentText(name));
    }

    @Override
    public void setHealth(double health) {
        setHealth((float) health);
    }
    
    @Override
    public Player getRescuer() {
        String name = (owner == null) ? lastOwner : owner;
        if (name == null) return null;
        Player rescuer = Bukkit.getPlayer(name);
        return rescuer;
    }
    
}
