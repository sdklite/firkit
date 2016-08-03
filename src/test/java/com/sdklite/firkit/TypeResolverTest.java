package com.sdklite.firkit;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.sdklite.firkit.util.TypeResolver;

import junit.framework.Assert;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/test/AndroidManifest.xml", sdk = 21)
public class TypeResolverTest {

    interface Parent<T> {
        
    }

    interface Callback<K, V, P> {
        
    }

    class MyClass implements Parent<Serializable>, Callback<MyClass, Void, Object> {
        
    }

    @Test
    public void resolve() {
        final Object obj0 = new MyClass();
        final Object obj1 = new Parent<Serializable>() {};
        final Callback<Void, Integer, Void> obj2 = new Callback<Void, Integer, Void>() {};
        Assert.assertEquals(Serializable.class, TypeResolver.getParameterizedGenericType(obj0, Parent.class));
        Assert.assertEquals(Serializable.class, TypeResolver.getParameterizedGenericType(obj1, Parent.class));
        Assert.assertEquals(Void.class, TypeResolver.getParameterizedGenericType(obj2, Callback.class));
        Assert.assertEquals(Integer.class, TypeResolver.getParameterizedGenericType(obj2, Callback.class, 1));
    }
    
}
