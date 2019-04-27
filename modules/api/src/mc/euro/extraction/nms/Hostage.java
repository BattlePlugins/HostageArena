package mc.euro.extraction.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

/**
 * CraftHostage implements Hostage. <br/><br/>
 * 
 * <pre>
 * Implementations: modules/{version}/CraftHostage.java
 * 
 * mc.euro.extraction.nms.v1_2_5.CraftHostage
 * mc.euro.extraction.nms.v1_3_2.CraftHostage
 * mc.euro.extraction.nms.v1_4_2.CraftHostage
 * mc.euro.extraction.nms.v1_4_5.CraftHostage
 * mc.euro.extraction.nms.v1_4_6.CraftHostage
 * mc.euro.extraction.nms.v1_4_R1.CraftHostage
 * mc.euro.extraction.nms.v1_5_R1.CraftHostage
 * mc.euro.extraction.nms.v1_5_R2.CraftHostage
 * mc.euro.extraction.nms.v1_5_R3.CraftHostage
 * mc.euro.extraction.nms.v1_6_R1.CraftHostage
 * mc.euro.extraction.nms.v1_6_R2.CraftHostage
 * mc.euro.extraction.nms.v1_6_R3.CraftHostage
 * mc.euro.extraction.nms.v1_7_R1.CraftHostage
 * mc.euro.extraction.nms.v1_7_R2.CraftHostage
 * mc.euro.extraction.nms.v1_7_R3.CraftHostage
 * mc.euro.extraction.nms.v1_7_R4.CraftHostage
 * mc.euro.extraction.nms.v1_8_R1.CraftHostage
 * mc.euro.extraction.nms.v1_8_R2.CraftHostage
 * mc.euro.extraction.nms.v1_8_R3.CraftHostage
 * mc.euro.extraction.nms.v1_9_R1.CraftHostage
 * </pre>
 * 
 * @author Nikolai
 */
public interface Hostage {
    
    public void stay();
    public boolean isStopped();
    public boolean isFollowing();
    public void follow(Player p);
    public void follow(String p);
    public void setOwner(Player p);
    public void setOwner(String name);
    public String getOwnerName();
    public Location getLocation();
    public void setLocation(Location loc);
    public void removeEntity();
    
    public Profession getProfessionType();
    public void setProfessionType(Profession x);
    // public String getCustomName();
    public void setCustomName(String name);
    
    public void setHealth(double health);
    
    public Player getRescuer();
    
}
