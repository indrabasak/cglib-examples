package com.basaki.cglib;

import net.sf.cglib.beans.ImmutableBean;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@code ImmutableBeanTest} tests {@code net.sf.cglib.beans.ImmutableBean}
 * utility of cglib. It creates immutable beans.
 *
 * @author Indra Basak
 * @since 11/2/17
 */
public class ImmutableBeanTest {

    /**
     * In this example the immutable bean prevents all state changes by throwing
     * an IllegalStateException. However, the state of the bean can be changed
     * by changing the original object. All such changes will be reflected by
     * the ImmutableBean.
     *
     * @throws Exception
     */
    @Test(expected = IllegalStateException.class)
    public void testImmutableBean() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setValue("Hello world!");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
        assertEquals("Hello world!", immutableBean.getValue());
        bean.setValue("Hello world, again!");
        assertEquals("Hello world, again!", immutableBean.getValue());

        // causes exception.
        immutableBean.setValue("Hello cglib!");
    }
}
