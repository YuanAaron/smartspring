package com.smartspring.beans.factory;

import com.smartspring.beans.BeanDefinition;

public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);
}
