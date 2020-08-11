package io.github.shiryu.aquabattlepets.api.file;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class ConfigurationOf {

    private final FileOf file;

    private FileConfiguration configuration;

    public ConfigurationOf(@NotNull final FileOf file){
        this.file = file;
    }

    public ConfigurationOf load(){
        this.configuration = YamlConfiguration.loadConfiguration(this.file.asFile().get());

        return this;
    }

    public ConfigurationOf save(){
        final File asFile = this.file.asFile().orElse(null);

        if (asFile != null){
            try{
                configuration.save(asFile);
                configuration.load(asFile);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return this;
    }


    public void set(@NotNull final String path, @NotNull final Object object){
        this.configuration.set(path, object);

        save();
    }

    public Optional<SectionOf> getSection(@NotNull final String path){
        final ConfigurationSection section = configuration.getConfigurationSection(path);

        if (configuration == null || section == null) return Optional.empty();

        return Optional.of(
                new SectionOf(
                        this,
                        section
                )
        );
    }

    @NotNull
    public <T> Optional<T> get(@NotNull final String path){
        if (this.configuration == null) return Optional.empty();

        T value = (T) this.configuration.get(path);

        if (value == null) return null;

        return Optional.ofNullable(value);
    }

    public <T> T getOrSet(@NotNull final String path, @NotNull final T fallback){
        if (fallback == null) return null;
        if (this.configuration == null) load();

        final T get = (T) get(path).orElse(null);

        if (get == null){
            set(path, fallback);

            return fallback;
        }

        return get;
    }
}
