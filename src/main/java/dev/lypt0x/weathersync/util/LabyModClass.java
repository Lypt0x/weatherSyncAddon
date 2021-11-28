package dev.lypt0x.weathersync.util;

import com.google.common.reflect.TypeToken;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LabyModClass {

    /**
     * Due to the fact that LabyMod writes unchecked Methods, we use reflection to call the specified Method.
     * On this way we avoid the need to suppress the unchecked warnings by annotations.
     * @param bound the instance of the type T
     * @param methodName the name of the method
     * @param <T> the type of the instance
     * @param <R> the return type of the method
     * @return the return value of the method
     */
    @SuppressWarnings("unchecked")
    public static <T, R> R invoke(@NotNull T bound, @NotNull String methodName) {
        try {
            Method method = bound.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (R)method.invoke(bound);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}
