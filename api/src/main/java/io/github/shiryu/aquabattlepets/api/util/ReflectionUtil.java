package io.github.shiryu.aquabattlepets.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.constructor.Construct;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ReflectionUtil {

    @NotNull
    public String getVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();

        return name.substring(name.lastIndexOf('.' + 1)) + 1;
    }

    @NotNull
    public Class<?> getClazz(@NotNull final String name){
        try{
            return Class.forName(name);
        }catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public Class<?> getNMSClass(@NotNull final String name){
        return getClazz("net.minecraft.server." + getVersion() + "." +  name);
    }

    @NotNull
    public Class<?> getCraftBukkitClass(@NotNull final String name){
        return getClazz("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    @NotNull
    public Class<?>[] getParameterTypes(@NotNull final Class<?> clazz){
        return (Class<?>[]) Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getType)
                .toArray();
    }

    @NotNull
    public Stream<Field> fieldStream(@NotNull final Class<?> clazz){
        return Arrays.stream(clazz.getDeclaredFields());
    }

    public void forEachField(@NotNull final Class<?> clazz, @NotNull final Consumer<Field> consumer){
        for (Field field : clazz.getDeclaredFields())
            consumer.accept(field);
    }


    @NotNull
    public Constructor findConstructor(@NotNull final Class<?> clazz, @NotNull final Class<?>... types){
        try{
            final Constructor constructor = clazz.getConstructor(types);

            constructor.setAccessible(true);

            return constructor;
        }catch (Exception e){
            return null;
        }
    }

    @NotNull
    public Constructor findConstructor(@NotNull final Class<?> clazz){
        return findConstructor(clazz);
    }

    @NotNull
    public <T> T newInstance(@NotNull final Constructor constructor, @NotNull final Object... parameters){
        try{
            return (T) constructor.newInstance(parameters);
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e){
            return null;
        }
    }

    @NotNull
    public <T> T newInstance(@NotNull final Constructor constructor){
        return newInstance(constructor);
    }

    @NotNull
    public Field findPrivateField(@NotNull final Class<?> clazz, @NotNull final String name){
        try{
            final Field field = clazz.getDeclaredField(name);

            field.setAccessible(true);

            return field;
        }catch (NoSuchFieldException e){
            return null;
        }
    }
    @NotNull
    public Field findField(@NotNull final Class<?> clazz, @NotNull final String name){
        try{
            final Field field = clazz.getField(name);
            
            field.setAccessible(true);
            
            return field;
        }catch(NoSuchFieldException e){
            return null;
        }
    }

    @NotNull
    public <T> T getField(@NotNull final Field field, @NotNull final Object base){
        try{
            return (T) field.get(base);
        }catch (IllegalAccessException e){
            return null;
        }
    }

    @NotNull
    public <T> T getField(@NotNull final Field field){
        return getField(field, null);
    }

    public void setField(@NotNull final Field field, @NotNull final Object base, @NotNull final Object set){
        try{
            field.set(base, set);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public void setField(@NotNull final Field field, @NotNull final Object set){
       setField(field, null, set);
    }

    @NotNull
    public Method findMethod(@NotNull final Class<?> clazz, @NotNull final String name, @NotNull final Class<?>... parameterTypes){
        try{
            final Method method = clazz.getMethod(name, parameterTypes);

            method.setAccessible(true);

            return method;
        }catch (NoSuchMethodException e){
            return null;
        }
    }

    @NotNull
    public Method findMethod(@NotNull final Class<?> clazz, @NotNull final String name){
        return findMethod(clazz, name);
    }

    public <T> T invokeMethod(@NotNull final Method method, @NotNull final Object base, @NotNull final Object... parameters){
        try{
            return (T) method.invoke(base, parameters);
        }catch (IllegalAccessException | InvocationTargetException e){
            return null;
        }
    }

    public <T> T invokeMethod(@NotNull final Method method, @NotNull final Object base){
        return invokeMethod(method, base);
    }

    public <T> T invokeMethodStatic(@NotNull final Method method, @NotNull final Object... parameters){
        return invokeMethod(method, null, parameters);
    }

    public <T> T invokeMethodStatic(@NotNull final Method method){
        return invokeMethodStatic(method);
    }

    @NotNull
    public Object getHandle(@NotNull final Object toBeHandle){
        return invokeMethod(
                findMethod(
                        toBeHandle.getClass(),
                        "getHandle"
                ),
                toBeHandle
        );
    }
    
    public void sendPacket(@NotNull final Player player, @NotNull final Object packet){
        final Object handle = getHandle(player);
        final Object playerConnection = getField(findField(handle.getClass(), "playerConnection"), handle);

        invokeMethod(
                findMethod(playerConnection.getClass(), "sendPacket", getNMSClass("Packet")),
                playerConnection
        );
    }

    public int getPing(@NotNull final Player player){
        final Object handle = getHandle(player);
        final Object playerConnection = getField(findField(handle.getClass(), "playerConnection"), handle);

        return getField(
                findField(
                        playerConnection.getClass(),
                        "ping"
                ),
                playerConnection
        );
    }
}
