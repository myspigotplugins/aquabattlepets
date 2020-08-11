package io.github.shiryu.aquabattlepets;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AquaBattlePets extends JavaPlugin {

    @Getter
    private static AquaBattlePets instance;

    @Override
    public void onEnable() {
        if (instance != null) throw new IllegalStateException("[AquaBattlePets] cannot be enable twice!");

        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
}
