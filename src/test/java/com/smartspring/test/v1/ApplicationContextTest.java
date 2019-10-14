package com.smartspring.test.v1;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.Context.support.ClassPathXmlApplicationContext;
import com.smartspring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
