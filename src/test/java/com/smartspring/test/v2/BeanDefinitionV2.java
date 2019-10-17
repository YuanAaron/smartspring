package com.smartspring.test.v2;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.PropertyValue;
import com.smartspring.beans.factory.config.RuntimeBeanReference;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.smartspring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionV2 {
    @Test
    public void testGetBeanDefinition() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();
        Assert.assertTrue(pvs.size() == 4);
        {
            PropertyValue pv = this.getPropertyValue("accountDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> pvs){
        for(PropertyValue pv : pvs){
            if(pv.getName().equals(name)){
                return pv;
            }
        }
        return null;
    }
}
