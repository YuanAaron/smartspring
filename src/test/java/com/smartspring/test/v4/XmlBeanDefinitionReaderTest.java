package com.smartspring.test.v4;

import com.smartspring.Context.annotation.ScannedGenericBeanDefinition;
import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.smartspring.core.annotation.AnnotationAttributes;
import com.smartspring.core.io.ClassPathResource;
import com.smartspring.core.io.Resource;
import com.smartspring.core.type.AnnotationMetadata;
import com.smartspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void testParseScanedBean(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);
        String annotation = Component.class.getName();

        //下面的代码和ClassPathBeanDefinitionScannerTest重复，该怎么处理？
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
