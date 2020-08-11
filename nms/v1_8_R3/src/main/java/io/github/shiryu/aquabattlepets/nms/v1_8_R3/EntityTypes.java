package io.github.shiryu.aquabattlepets.nms.v1_8_R3;

import io.github.shiryu.aquabattlepets.api.util.ReflectionUtil;
import io.github.shiryu.aquabattlepets.nms.v1_8_R3.pets.PetBat;
import io.github.shiryu.aquabattlepets.nms.v1_8_R3.pets.PetWither;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EntityTypes {

    BAT("Bat", 65, PetBat.class),
    WITHER("Wither", 64, PetWither.class);

    private static Map<String, Class<? extends Entity>> mobTypes = new HashMap<>();

    static{
        loadMobTypes(
                EntityMagmaCube.class,
                EntitySlime.class,
                EntityCaveSpider.class,
                EntityChicken.class,
                EntityCow.class,
                EntityCreeper.class,
                EntityHorse.class,
                EntityIronGolem.class,
                EntityMushroomCow.class,
                EntityOcelot.class,
                EntityPig.class,
                EntityPigZombie.class,
                EntityRabbit.class,
                EntitySheep.class,
                EntitySilverfish.class,
                EntitySkeleton.class,
                EntitySnowman.class,
                EntitySpider.class,
                EntityVillager.class,
                EntityWolf.class,
                EntityZombie.class,
                EntityWitch.class,
                PetBat.class,
                PetWither.class
        );
    }
    EntityTypes(@NotNull final String name, final int id, @NotNull final Class<? extends Entity> clazz){
        init(name, id, clazz);
    }

    @NotNull
    private static String getSimpleName(@NotNull final Class<? extends Entity> clazz){
        return clazz.getSimpleName().replaceAll("Entity", "").toLowerCase();
    }

    @NotNull
    private static void loadMobTypes(@NotNull final Class<? extends Entity>... classes){
        if (classes.length == 0) return;

        for (Class<? extends Entity> clazz : classes)
            mobTypes.put(
                    getSimpleName(clazz),
                    clazz
            );

    }
    @NotNull
    public static Entity create(@NotNull final String name, @NotNull final World world){
        final Entity entity;

        if (!mobTypes.containsKey(name)){
            System.out.println("[AquaBattlePets] not found entity type : " + name);

            return null;
        }

        final Class<? extends Entity> clazz = mobTypes.get(name);

        if (name.equals("wither") || name.equals("bat")){
            entity = ReflectionUtil.newInstance(
                    ReflectionUtil.findConstructor(
                            clazz,
                            World.class,
                            boolean.class
                    ),
                    world,
                    true
            );
        }else{
            entity = ReflectionUtil.newInstance(
                    ReflectionUtil.findConstructor(
                            clazz,
                            World.class
                    ),
                    world
            );
        }

        return entity;
    }

    @NotNull
    public static Entity spawn(@NotNull final Entity entity, @NotNull final Location location){
        final World world = entity.world;

        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        final int x = MathHelper.floor(entity.locX / 16.0D);
        final int j = MathHelper.floor(entity.locY / 16.0D);

        world.getChunkAt(x, j).a(entity);
        world.entityList.add(entity);

        final List u = ReflectionUtil.getField(
                ReflectionUtil.findPrivateField(
                        World.class,
                        "u"
                ),
                world
        );

        for (int i = 0; i < u.size(); i++)
            ((IWorldAccess) u.get(i)).a(entity);

        final IntHashMap entities = ReflectionUtil.getField(
                ReflectionUtil.findPrivateField(
                        World.class,
                        "entitiesById"
                ),
                world
        );

        entities.a(entity.getId(), entity);
        entity.valid = true;

        return entity;
    }

    private void init(@NotNull final String name, final int id, @NotNull final Class<? extends Entity> clazz){
        final Class<?> entityTypesClass = net.minecraft.server.v1_8_R3.EntityTypes.class;

        ReflectionUtil.setField(
                ReflectionUtil.findPrivateField(
                        entityTypesClass,
                        "c"
                ),
                name,
                clazz
        );

        ReflectionUtil.setField(
                ReflectionUtil.findPrivateField(
                        entityTypesClass,
                        "d"
                ),
                clazz,
                name
        );

        ReflectionUtil.setField(
                ReflectionUtil.findPrivateField(
                        entityTypesClass,
                        "f"
                ),
                clazz,
                id
        );
    }
}
