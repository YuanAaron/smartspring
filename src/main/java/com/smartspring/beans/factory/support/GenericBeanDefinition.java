package com.smartspring.beans.factory.support;

import com.smartspring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {
    private String id; //对应xml中的id
    private String beanClassName; //对应xml中的class

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id=id;
        this.beanClassName=beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
