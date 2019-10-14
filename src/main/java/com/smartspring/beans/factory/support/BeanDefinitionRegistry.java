package com.smartspring.beans.factory.support;

import com.smartspring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanId,BeanDefinition bd);
    BeanDefinition getBeanDefinition(String beanId);
}
