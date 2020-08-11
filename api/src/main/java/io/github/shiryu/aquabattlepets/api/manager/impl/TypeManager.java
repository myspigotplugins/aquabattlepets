package io.github.shiryu.aquabattlepets.api.manager.impl;

import io.github.shiryu.aquabattlepets.api.BattlePet;
import io.github.shiryu.aquabattlepets.api.BattlePetMeta;
import io.github.shiryu.aquabattlepets.api.BattlePetType;
import io.github.shiryu.aquabattlepets.api.file.FileOf;
import io.github.shiryu.aquabattlepets.api.manager.Manager;
import io.github.shiryu.aquabattlepets.api.util.ReflectionUtil;
import lombok.Getter;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class TypeManager implements Manager {

    private final Map<String, BattlePetType> TYPES = new HashMap<>();

    @Override
    public void handle(@NotNull final Plugin plugin) {
        TYPES.clear();

        new FileOf("/types/", "")
                .create(plugin, true)
                .forEach(file ->{
                    final Object[] parameters = ReflectionUtil.fieldStream(BattlePetMeta.class)
                            .map(field -> file.config().getOrSet(field.getName(), null))
                            .toArray();

                    final EntityType entityType = EntityType.valueOf(file.config().getOrSet("entityType", "BAT"));

                    final BattlePetMeta meta = ReflectionUtil.newInstance(
                            ReflectionUtil.findConstructor(
                                    BattlePetMeta.class,
                                    ReflectionUtil.getParameterTypes(BattlePetMeta.class)
                            ),
                            parameters
                    );

                    TYPES.put(
                            file.name(),
                            new BattlePetType(
                                    file.name(),
                                    meta,
                                    entityType
                            )
                    );
                });
    }

    @NotNull
    public Optional<BattlePetType> findType(@NotNull final String type){
        return Optional.ofNullable(
                TYPES.get(type)
        );
    }

}
