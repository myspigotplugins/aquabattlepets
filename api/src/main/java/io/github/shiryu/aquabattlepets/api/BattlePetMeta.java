package io.github.shiryu.aquabattlepets.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BattlePetMeta {

    private final boolean rideable;
    private final int reqLevel;
    private final int minHP;
    private final int chance;
    private final int maxLevel;
    private final double xpForLevel;
    private final int skillPointForLevel;
    private final double hpPerSecPercent;
    private final double hp,damage,defense,speed;
    private final int maxvit,maxstr,maxdef,maxdev;

}
