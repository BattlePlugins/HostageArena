package mc.euro.extraction.nms.v1_13_R2;

import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_13_R2.DataConverterRegistry;
import net.minecraft.server.v1_13_R2.DataConverterTypes;
import net.minecraft.server.v1_13_R2.Entity;
import net.minecraft.server.v1_13_R2.EntityInsentient;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EntityVillager;

import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.World;

/**
 * http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/
 *
 * @author TeeePeee
 */
public enum CustomEntityType {

    HOSTAGE("hostage", EntityTypes.VILLAGER, EntityVillager.class, CraftHostage.class);

    public String author = "TeeePeee";
    public String source = "http://forums.bukkit.org/threads/nms-tutorial-how-to-override-default-minecraft-mobs.216788/";

    private String name;
    private EntityTypes<? extends EntityInsentient> entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    private CustomEntityType(String name, EntityTypes<? extends EntityInsentient> entityType,
            Class<? extends EntityInsentient> nmsClass,
            Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName() {
        return name;
    }

    public EntityTypes getEntityType() {
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
            registerCustomEntity(entity, CraftHostage::new);
        }
    }

    /**
     * Unregister our entities to prevent memory leaks. Call on disable.
     */
    public static void unregisterEntities() {
        for (CustomEntityType entity : values()) {
            MinecraftKey minecraftKey = MinecraftKey.a(entity.getName());

            // Unsure if this works fully, but we should be fine...
            Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
            typeMap.remove(minecraftKey);
        }
    }

    private static void registerCustomEntity(CustomEntityType type, Function<? super World, ? extends Entity> worldFunction) {
        MinecraftKey minecraftKey = MinecraftKey.a(type.getName());

        Map<Object, Type<?>> typeMap = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        typeMap.put(minecraftKey.toString(), typeMap.get(MinecraftKey.a(type.getName()).toString()));

        EntityTypes.a(type.getName(), EntityTypes.a.a(type.getCustomClass(), worldFunction));
    }
}
