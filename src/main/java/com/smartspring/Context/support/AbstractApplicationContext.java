package com.smartspring.Context.support;

import com.smartspring.Context.ApplicationContext;
import com.smartspring.beans.factory.support.DefaultBeanFactory;
import com.smartspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.smartspring.core.io.FileSystemResource;
import com.smartspring.core.io.Resource;
import com.smartspring.util.ClassUtils;

//抽象类与抽象方法：
//1、抽象类可以有抽象方法和非抽象方法，其中非抽象方法往往都是抽象类的所有子类所具有的，而抽象方法则由具体的不同子类实现不同的方法
//2、抽象类可以没有抽象方法，但有抽象方法的类一定是抽象类

public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory=null;
    private ClassLoader beanClassLoader=null;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(factory);
        Resource resource=getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
        //设置和获取beanClassLoader的方法有问题，后面修复
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader=beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader!=null?this.beanClassLoader: ClassUtils.getDefaultClassLoader();
    }
}
