package io.tofpu.dynamicmessages.util;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setField(final Object instance, final Field field, final Object value) {
        try {
            field.set(instance, value);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException("Could not set field " + field.getName() +
                                            " to " + value, e);
        }
    }

    public static <T> T readField(final Object instance, final Field field) {
        try {
            return (T) field.get(instance);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException("Could not read field " + field.getName(), e);
        }
    }
}
