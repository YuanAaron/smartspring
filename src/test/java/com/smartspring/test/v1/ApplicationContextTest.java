package com.smartspring.test.v1;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.Context.support.ClassPathXmlApplicationContext;
import com.smartspring.Context.support.FileSystemXmlApplicationContext;
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

    @Test
    public void testGetBeanFromFileSystemContext() {
        //注意：这里仍然是hardcode了一个本地路径，这是不好的实践！！！如何处理，留作作业
        ApplicationContext context=new FileSystemXmlApplicationContext("E:\\IDEAworkspace\\petstore-v1.xml");
        PetStoreService petStore=(PetStoreService)context.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
