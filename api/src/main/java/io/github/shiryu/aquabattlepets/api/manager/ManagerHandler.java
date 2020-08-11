package io.github.shiryu.aquabattlepets.api.manager;

import io.github.shiryu.aquabattlepets.api.manager.impl.TypeManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ManagerHandler {

    private List<Manager> managers = new ArrayList<>();

    @Getter
    private static ManagerHandler instance = new ManagerHandler();

    private ManagerHandler(){
        createManagers(
                TypeManager.class
        );
    }

    @NotNull
    public List<Manager> createManagers(@NotNull final Class<?>... elementTypes){
        final List<Manager> managers = new ArrayList<>();

        if (elementTypes.length == 0) return managers;

        for (Class<?> elementType : elementTypes){
            managers.add(
                    create(
                            elementType,
                            "handle"
                    )
            );
        }

        return managers;
    }

    @NotNull
    public <T> T create(@NotNull final Class<?> elementType){
        try{
            final Constructor<?> constructor = elementType.getConstructor();

            return (T) constructor.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @NotNull
    public <T> T create(@NotNull final Class<?> elementType, @NotNull final String methodName, @NotNull final Object... values){
        try{
            final T object = create(elementType);

            Method method;

            if (values.length > 0){
                final Class<?>[] types = new Class<?>[values.length];

                for (int i = 0; i < values.length; i++) types[i] = values[i].getClass();

                method = elementType.getMethod(
                        methodName,
                        types
                );

                method.invoke(object, values);
            }else{
                method = elementType.getMethod(methodName);

                method.invoke(object);
            }

            return object;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @NotNull
    public <T> T get(@NotNull final Class<T> elementType, @NotNull final List list){
        return (T) this.managers.stream()
                .filter(x -> x.getClass().equals(elementType))
                .findAny()
                .orElse(null);
    }

    @NotNull
    public <T> T getManager(@NotNull final Class<T> elementType){
        return get(elementType, this.managers);
    }
}
