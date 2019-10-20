package com.smartspring.beans.factory.config;

import com.smartspring.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
    boolean afterInstantiation(Object bean, String beanName) throws BeansException;
    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}