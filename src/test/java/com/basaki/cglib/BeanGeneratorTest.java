package com.basaki.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.beans.BeanGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@code BeanGeneratorTest} tests {@code net.sf.cglib.beans.BeanGenerator}
 * utility of cglib. It creates new beans at run time.
 *
 * @author Indra Basak
 * @since 11/2/17
 */
public class BeanGeneratorTest {

    /**
     * The BeanGenerator class takes any property as name value pairs. Once the
     * create method is invoked, the BeanGenerator creates the accessors:
     * <p/>
     * <type> get<name>()
     * </p>
     * void set<name>(<type>)
     * <p/>
     * This might be useful when another library expects beans which are
     * resolved by reflection.
     *
     * @throws Exception
     */
    @Test
    public void testBeanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object myBean = beanGenerator.create();

        Method setter = myBean.getClass().getMethod("setValue", String.class);
        setter.invoke(myBean, "Hello cglib!");
        Method getter = myBean.getClass().getMethod("getValue");
        assertEquals("Hello cglib!", getter.invoke(myBean));
    }
}
