package io.github.shiryu.aquabattlepets.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class BattlePetType {

    private final String name;
    private final BattlePetMeta meta;
    private final EntityType entityType;
}
