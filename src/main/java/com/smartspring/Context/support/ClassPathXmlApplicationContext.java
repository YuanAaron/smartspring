package com.smartspring.Context.support;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory=null;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(configFile);
    }

    @Override
    public Object getBean(String beanId) {
        return this.factory.getBean(beanId);
    }
}
