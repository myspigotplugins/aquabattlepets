package io.github.shiryu.aquabattlepets.api.file;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class FileOf {


    private final String name;
    private final String path;

    private File file;
    private Plugin plugin;
    private boolean loadDefaults = false;

    public FileOf(@NotNull final String name, @NotNull final String path){
        this.name = name;
        this.path = path;
    }

    public FileOf loadDefaults(final boolean loadDefaults){
        this.loadDefaults = loadDefaults;

        return this;
    }

    @NotNull
    public FileOf create(@NotNull final Plugin plugin, final boolean mkdir){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/" + this.name + "/");

        this.file.getParentFile().mkdirs();

        if (mkdir && !file.exists()){
            file.mkdirs();

            return this;
        }

        if (file.exists()){
            config().load();

            return this;
        }

        if (this.loadDefaults){
            copy(
                    plugin.getResource(this.name)
            );

            return this;
        }

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @NotNull
    public String name(){
        final AtomicReference<String> name = new AtomicReference<>("");

        asFile().ifPresent(x -> name.set(x.getName().replaceAll(".yml", "")));

        return name.get();
    }

    @NotNull
    public Optional<FileOf> findFile(@NotNull final String name){
        if (this.file == null || this.file.listFiles() == null) return Optional.empty();

        for (File file : this.file.listFiles()){
            if (file.getName() == name){
                return Optional.of(
                        new FileOf(
                                name,
                                this.file.getPath()
                        )
                );
            }
        }

        return Optional.empty();
    }

    public void forEach(@NotNull final Consumer<FileOf> consumer){
        if (this.file == null || this.file.listFiles() == null) return;

        for (File file : this.file.listFiles()){
            final FileOf fileOf = new FileOf(
                    file.getName(),
                    file.getPath()
            );


            fileOf.create(this.plugin, false);

            System.out.println(fileOf.asFile().orElse(null).getPath());
            consumer.accept(
                    fileOf
            );
        }
    }

    @NotNull
    public ConfigurationOf config(){
        return new ConfigurationOf(this).load();
    }

    @NotNull
    public Optional<File> asFile(){
        return Optional.ofNullable(
                this.file
        );
    }

    private void copy(@NotNull final InputStream inputStream) {
        try (OutputStream out = new FileOutputStream(this.file)) {
            byte[] buf = new byte[1024];

            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            inputStream.close();
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

}
