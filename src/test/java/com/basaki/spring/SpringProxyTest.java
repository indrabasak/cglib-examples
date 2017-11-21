package com.basaki.spring;

import java.lang.reflect.Method;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.util.ClassUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SpringProxyTest {

    @Test
    public void testProxyIsJustInterface() throws Throwable {
        ServiceImpl raw = new ServiceImpl();

        AdvisedSupport config = new AdvisedSupport(Service.class);
        config.setTarget(raw);

        DefaultAopProxyFactory factory = new DefaultAopProxyFactory();
        AopProxy aopProxy = factory.createAopProxy(config);

        Object proxy = aopProxy.getProxy();
        System.out.println("&& " + proxy);
        assertTrue(proxy instanceof Service);
        assertFalse(proxy instanceof ServiceImpl);
        assertEquals("2", ((Service) proxy).getString(2));
    }

    @Test
    public void testProxyWithoutInterface() throws Throwable {
        ServiceImpl raw = new ServiceImpl();

        AdvisedSupport config = new AdvisedSupport();
        config.setTarget(raw);

        DefaultAopProxyFactory factory = new DefaultAopProxyFactory();
        AopProxy aopProxy = factory.createAopProxy(config);

        Object proxy = aopProxy.getProxy();
        System.out.println(" ** " + proxy);
        assertTrue(proxy instanceof Service);
        assertTrue(proxy instanceof ServiceImpl);
        assertEquals("2", ((Service) proxy).getString(2));
    }

    @Test
    public void testProxyIsJustInterfaceWithAdvisor() throws Throwable {
        ServiceImpl raw = new ServiceImpl();

        AdvisedSupport config = new AdvisedSupport(Service.class);
        config.setTarget(raw);
        config.addAdvisor(
                createNonNegativeAdvisor(Service.class, "getString"));
        config.addAdvisor(createCacheAdvisor());

        DefaultAopProxyFactory factory = new DefaultAopProxyFactory();
        AopProxy aopProxy = factory.createAopProxy(config);

        Object proxy = aopProxy.getProxy();
        assertTrue(proxy instanceof Service);
        assertFalse(proxy instanceof ServiceImpl);
        assertEquals("2", ((Service) proxy).getString(2));
    }

    @Test
    public void testProxyWithoutInterfaceWithAdvisor() throws Throwable {
        ServiceImpl raw = new ServiceImpl();

        AdvisedSupport config = new AdvisedSupport();
        config.setTarget(raw);
        config.addAdvisor(
                createNonNegativeAdvisor(ServiceImpl.class, "getString"));
        //ProxtFactory.getFactory
        //ProxyCreatorSupport.createAopProxy()

        DefaultAopProxyFactory factory = new DefaultAopProxyFactory();
        AopProxy aopProxy = factory.createAopProxy(config);

        Object proxy = aopProxy.getProxy();
        assertTrue(proxy instanceof Service);
        assertTrue(proxy instanceof ServiceImpl);
        assertEquals("2", ((ServiceImpl) proxy).getString(2));
    }

    @Test
    public void testNestedProxyIsJustInterfaceWithAdvisor() throws Throwable {
        ServiceImpl raw = new ServiceImpl();
        AdvisedSupport config = new AdvisedSupport(Service.class);
        config.setTarget(raw);
        config.addAdvisor(createCacheAdvisor());

        DefaultAopProxyFactory factory = new DefaultAopProxyFactory();
        AopProxy aopProxy = factory.createAopProxy(config);
        Object proxy = aopProxy.getProxy();

        AdvisedSupport config2 = new AdvisedSupport(
                ClassUtils.getAllInterfaces(proxy));
        config2.setTarget(proxy);
        config2.addAdvisor(createNonNegativeAdvisor(ServiceImpl.class, "getString"));

        DefaultAopProxyFactory factory2 = new DefaultAopProxyFactory();
        AopProxy aopProxy2 = factory.createAopProxy(config2);
        Object proxy2 = aopProxy2.getProxy();


        assertTrue(proxy2 instanceof Service);
        assertFalse(proxy2 instanceof ServiceImpl);
        assertEquals("2", ((Service) proxy2).getString(2));
    }

    private Advisor createNonNegativeAdvisor(Class clazz, String methodName) {
        NameMatchMethodPointcutAdvisor advisor =
                new NameMatchMethodPointcutAdvisor();
        advisor.setClassFilter(new RootClassFilter(clazz));
        advisor.addMethodName(methodName);
        advisor.setAdvice(new EnsureNonNegativeAdvice());

        return advisor;
    }

    private Advisor createCacheAdvisor() {
        BeanFactoryCacheOperationSourceAdvisor advisor =
                new BeanFactoryCacheOperationSourceAdvisor();
        advisor.setCacheOperationSource(new AnnotationCacheOperationSource());

        CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSources(new AnnotationCacheOperationSource());
        advisor.setAdvice(interceptor);

        advisor.setOrder(2147483647);

        return advisor;
    }

    public static class EnsureNonNegativeAdvice implements MethodBeforeAdvice {

        @Override
        public void before(Method method, Object[] args, Object target)
                throws Throwable {
            System.out.println("method: " + method);
            if ((int) args[0] < 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
