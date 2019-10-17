package com.smartspring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanDefinitionV2.class,
        BeanDefinitionValueResolverTest.class,
        ApplicationContextTestV2.class,
        CustomNumberEditorTest.class,
        CustomBooleanEditorTest.class,
        TypeConverterTest.class})
public class V2AllTests {
}
