package com.smartspring.test.v2;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.Context.support.ClassPathXmlApplicationContext;
import com.smartspring.dao.v2.AccountDao;
import com.smartspring.dao.v2.ItemDao;
import com.smartspring.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

        Assert.assertEquals("zhangsan",petStore.getOwner());
    }
}
