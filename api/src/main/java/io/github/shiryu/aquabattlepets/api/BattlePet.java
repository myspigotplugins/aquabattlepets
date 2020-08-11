package io.github.shiryu.aquabattlepets.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BattlePet {

    private final BattlePetType type;
    private final UUID owner;
    private final LivingEntity entity;
}
