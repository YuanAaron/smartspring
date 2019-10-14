package com.smartspring.Context.support;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.smartspring.core.io.FileSystemResource;

public class FileSystemXmlApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory=null;

    public FileSystemXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new FileSystemResource(configFile));
    }

    @Override
    public Object getBean(String beanId) {
        return this.factory.getBean(beanId);
    }
}
