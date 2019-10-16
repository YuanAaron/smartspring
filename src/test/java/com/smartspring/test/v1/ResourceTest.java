package com.smartspring.test.v1;

import com.smartspring.core.io.ClassPathResource;
import com.smartspring.core.io.FileSystemResource;
import com.smartspring.core.io.Resource;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource r=new ClassPathResource("petstore-v1.xml");
        InputStream is= null;
        try {
            is=r.getInputStream();
            //注意：这个测试其实并不充分。
            //充分的测试应该把is的内容读出来与petstore-v1.xml进行比较
            assertNotNull(is);
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        //不好的写法，这里仅仅是为了展示FileSystemResource,如何处理留作作业
        Resource r=new FileSystemResource("src\\test\\resource\\petstore-v1.xml");
        InputStream is= null;
        try {
            is=r.getInputStream();
            //注意：这个测试其实并不充分
            assertNotNull(is);
        } finally {
            if (is!=null) {
                is.close();
            }
        }
    }
}