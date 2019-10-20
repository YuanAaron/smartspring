package com.smartspring.beans.factory.config;

import com.smartspring.beans.BeansException;

public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws BeansException;
    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
