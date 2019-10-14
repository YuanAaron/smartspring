package com.smartspring.test.v1;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.factory.BeanCreationException;
import com.smartspring.beans.factory.BeanDefinitionStoreException;
import com.smartspring.beans.factory.BeanFactory;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.smartspring.core.io.ClassPathResource;
import com.smartspring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {
    DefaultBeanFactory factory=null;
    XmlBeanDefinitionReader reader=null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader=new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        //获取Bean的定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertEquals("com.smartspring.service.v1.PetStoreService",bd.getBeanClassName());
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        try{
            factory.getBean("invalidBean");
        }catch(BeanCreationException e){
            return;
        }
        Assert.fail("expect BeanCreationException ");
    }

    @Test
    public void testInvalidXML(){
        try{
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        }catch(BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }
}