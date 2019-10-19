package com.smartspring.test.v4;

import com.smartspring.core.annotation.AnnotationAttributes;
import com.smartspring.core.io.ClassPathResource;
import com.smartspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import com.smartspring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void testGetClasMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/smartspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        //在这个过程中，visitor会调用visit方法
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("com.smartspring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("com/smartspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "com.smartspring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));
        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
    }
}
