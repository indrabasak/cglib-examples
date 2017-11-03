package com.basaki.reflect;

import java.lang.reflect.Proxy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProxyTest {

    @Test
    public void testDebugProxyInvocationHandler() throws BazException {
        Foo foo = (Foo) DebugProxyInvocationHandler.newInstance(new FooImpl());
        assertEquals("Hello World!", foo.bar(null));
    }

    @Test
    public void testDelegatorInvocationHandler() throws BazException {
        Class[] proxyInterfaces = new Class[]{Foo.class};
        Foo foo = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(),
                proxyInterfaces,
                new DelegatorInvocationHandler(proxyInterfaces,
                        new Object[]{new FooImpl()}));
        assertEquals("Hello World!", foo.bar(null));
        System.out.println(foo.hashCode());
    }
}
