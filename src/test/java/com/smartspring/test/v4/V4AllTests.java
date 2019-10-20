package com.smartspring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PackageResourceLoaderTest.class,
        ClassReaderTest.class,
        MetadataReaderTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        XmlBeanDefinitionReaderTest.class})
public class V4AllTests {
}
