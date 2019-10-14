package com.smartspring.test.v1;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.factory.BeanFactory;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.service.v1.PetStoreService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

    @Test
    public void testGetBean() {
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        //获取Bean的定义
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertEquals("com.smartspring.service.v1.PetStoreService",bd.getBeanClassName());
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);
    }
}