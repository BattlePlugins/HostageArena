package mc.euro.extraction.nms.v1_4_5;

import mc.euro.extraction.api.ExtractionPlugin;
import mc.euro.extraction.nms.Hostage;
import mc.euro.extraction.nms.NPCFactory;
import mc.euro.extraction.util.Villagers;

import net.minecraft.server.v1_4_5.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_4_5.CraftWorld;
import org.bukkit.craftbukkit.v1_4_5.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

/**
 * 
 * 
 * @author Nikolai
 */
public class HostageFactory extends NPCFactory {
    
    public HostageFactory(ExtractionPlugin p) {
        this.plugin = p;
    }

    @Override
    public Hostage getHostage(Entity entity) {
        Hostage h;
        try {
            h = (Hostage) ((CraftEntity)entity).getHandle();
        } catch (ClassCastException ex) {
            // Caused by baby villager or a non-Hostage Villager.
            plugin.debug().log("onHostageInteract() ClassCastException: most likely "
                    + "caused by a baby villager or a Villager that is not a Hostage.");
            
            Villager v = (Villager) entity;
            World world = ((CraftWorld) v.getWorld()).getHandle();
            
            CraftHostage hostage = new CraftHostage(world, v.getProfession().getId());
            hostage.setLocation(v.getLocation());
            
            world.removeEntity(((CraftEntity)entity).getHandle());
            world.addEntity(hostage);
            
            hostage.setHealth((float) v.getHealth());
            hostage.setProfessionType(v.getProfession());
            hostage.setCustomName(Villagers.getName());
            
            return hostage;
        }
        return h;
    }

    @Override
    public Hostage spawnHostage(Location loc) {
        Profession profession = Villagers.getRandomType();
        return spawnHostage(loc, profession);
    }
    
    @Override
    public Hostage spawnHostage(Location loc, Profession prof) {
        World world = ((CraftWorld) loc.getWorld()).getHandle();
        CraftHostage hostage = new CraftHostage(world, prof.getId());
        hostage.setLocation(loc);
        world.addEntity(hostage);
        return hostage;
    }

    @Override
    public boolean isHostage(Entity entity) {
        Hostage h;
        try {
            h = (Hostage) ((CraftEntity)entity).getHandle();
        } catch (ClassCastException ex) {
            return false;
        }
        return true;
    }

}
