package mc.euro.extraction.nms.v1_12_R1;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_12_R1.BiomeBase;
import net.minecraft.server.v1_12_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.EntityVillager;

import net.minecraft.server.v1_12_R1.MinecraftKey;
import org.bukkit.entity.EntityType;

/**
 * http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/
 *
 * @author TeeePeee
 */
public enum CustomEntityType {

    HOSTAGE("Villager", 120, EntityType.VILLAGER, EntityVillager.class, CraftHostage.class);

    public String author = "TeeePeee";
    public String source = "http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/";

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    private CustomEntityType(String name, int id, EntityType entityType,
            Class<? extends EntityInsentient> nmsClass,
            Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<? extends EntityInsentient> getNMSClass() {
        return nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass() {
        return customClass;
    }

    /**
     * Register our entities.
     */
    public static void registerEntities() {
        for (CustomEntityType entity : values()) {
            // a(entity.getCustomClass(), entity.getName(), entity.getID());
            CustomEntityRegistry.registerCustomEntity(entity.getID(), entity.getName(), entity.getCustomClass());
        }

        for (BiomeBase biomeBase : BiomeBase.REGISTRY_ID) {
            if (biomeBase == null) {
                break;
            }

// Names changed from J, K, L and M.
// Names changed from as, at, au, av
// Names changed from aw, at, au, av
            for (String field : new String[]{"t", "u", "v", "w"}) {
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

// Write in our custom class.
                    for (BiomeMeta meta : mobList) {
                        for (CustomEntityType entity : values()) {
                            if (entity.getNMSClass().equals(meta.b)) {
                                meta.b = entity.getCustomClass();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Unregister our entities to prevent memory leaks. Call on disable.
     */
    public static void unregisterEntities() {
        for (CustomEntityType entity : values()) {
// Remove our class references.
            CustomEntityRegistry.unregisterCustomEntity(entity.getID(), entity.getName(), entity.getCustomClass());
        }

        for (CustomEntityType entity : values()) {
            try {
// Unregister each entity by writing the NMS back in place of the custom class.
                // a(entity.getNMSClass(), entity.getName(), entity.getID());
                CustomEntityRegistry.unregisterCustomEntity(entity.getID(), entity.getName(), entity.getCustomClass());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (BiomeBase biomeBase : BiomeBase.REGISTRY_ID) {
            if (biomeBase == null) {
                break;
            }

// The list fields changed names but update the meta regardless.
            for (String field : new String[]{"t", "u", "v", "w"}) {
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

// Make sure the NMS class is written back over our custom class.
                    for (BiomeMeta meta : mobList) {
                        for (CustomEntityType entity : values()) {
                            if (entity.getCustomClass().equals(meta.b)) {
                                meta.b = entity.getNMSClass();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
