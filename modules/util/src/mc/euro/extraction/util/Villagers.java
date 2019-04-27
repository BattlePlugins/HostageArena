package mc.euro.extraction.util;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Nikolai
 */
public class Villagers {
    
    private static int tCounter = 0; // type counter
    private static int nCounter = 0; // name counter
    
    public static Profession getType() {
        return getType("HostageTypes");
    }
    
    private static Profession getType(String path) {
        Profession p = Profession.FARMER;
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HostageArena");
        Villagers.tCounter = Villagers.tCounter + 1;
        List<String> professions = plugin.getConfig().getStringList(path);
        if (professions != null && professions.size() > 0 && Villagers.tCounter <= professions.size()) {
            try {
                p = Profession.valueOf(professions.get(tCounter - 1));
            } catch (IllegalArgumentException ex) {
                p = getRandomType();
            }
            if (Villagers.tCounter >= professions.size()) Villagers.tCounter = 0;
        }
        return p;
    }
    
    public static Profession getRandomType() {
        Profession[] professions = new Profession[] {Profession.FARMER, Profession.LIBRARIAN, Profession.PRIEST, Profession.BLACKSMITH, Profession.BUTCHER};
        return professions[(new Random().nextInt(5))];
    }
    
    public static String getName() {
        return getName("HostageNames");
    }
    
    private static String getName(String path) {
        String name = "VIP";
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HostageArena");
        Villagers.nCounter = Villagers.nCounter + 1;
        List<String> names = plugin.getConfig().getStringList(path);
        if (names != null && names.size() > 0 && Villagers.nCounter <= names.size()) {
            name = names.get(nCounter - 1);
            if (Villagers.nCounter >= names.size()) Villagers.nCounter = 0;
        }
        return name;
    }
}
