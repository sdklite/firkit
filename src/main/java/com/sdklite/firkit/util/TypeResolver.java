package com.sdklite.firkit.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeResolver {

    public static final Type getParameterizedGenericType(final Object obj, final Class<?> interfaceType) {
        final Class<?> clazz = obj.getClass();
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (null == genericInterfaces || genericInterfaces.length <= 0) {
            throw new IllegalArgumentException("No generic interfaces found");
        }

        for (final Type genericInterface : genericInterfaces) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            if (parameterizedType.getRawType() == interfaceType) {
                return parameterizedType.getActualTypeArguments()[0];
            }
        }

        throw new IllegalArgumentException("Parameterized generic type not found");
    }

    public static final Type getParameterizedGenericType(final Object obj, final Class<?> interfaceType, final int indexOfTypeArgument) {
        final Class<?> clazz = obj.getClass();
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (null == genericInterfaces || genericInterfaces.length <= 0) {
            throw new IllegalArgumentException("No generic interfaces found");
        }

        for (final Type genericInterface : genericInterfaces) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            if (parameterizedType.getRawType() == interfaceType) {
                return parameterizedType.getActualTypeArguments()[indexOfTypeArgument];
            }
        }

        throw new IllegalArgumentException("Parameterized generic type not found");
    }

    public static final Type getParameterizedGenericType(final Object obj, final int indexOfInterface, final int indexOfTypeArgument) {
        final Class<?> clazz = obj.getClass();
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (null == genericInterfaces || genericInterfaces.length <= 0) {
            throw new IllegalArgumentException("No generic interfaces found");
        }

        final Type genericInterface = genericInterfaces[indexOfInterface];
        if (!(genericInterface instanceof ParameterizedType)) {
            throw new IllegalArgumentException(genericInterface + " was supposed to be " + ParameterizedType.class);
        }

        final ParameterizedType type = (ParameterizedType) genericInterface;
        return type.getActualTypeArguments()[indexOfTypeArgument];

    }

    private TypeResolver() {
    }
}
