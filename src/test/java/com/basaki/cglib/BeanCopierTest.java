package com.basaki.cglib;

import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@code BeanCopierTest} tests {@code net.sf.cglib.beans.BeanCopier}
 * utility of cglib. It copies beans by property values.
 *
 * @author Indra Basak
 * @since 11/2/17
 */
public class BeanCopierTest {

    /**
     * Copies properties without being restrained to a specific type. The
     * BeanCopier#copy method takles an (eventually) optional Converter which
     * allows for further manipulations of bean properties. If the
     * BeanCopier is created with false as the third constructor argument, the
     * Converter is ignored and can therefore be null.
     *
     * @throws Exception
     */
    @Test
    public void testBeanCopier() throws Exception {
        BeanCopier copier =
                BeanCopier.create(SampleBean.class, OtherSampleBean.class,
                        false);
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        OtherSampleBean otherBean = new OtherSampleBean();
        copier.copy(bean, otherBean, null);
        assertEquals("Hello cglib!", otherBean.getValue());
    }
}
