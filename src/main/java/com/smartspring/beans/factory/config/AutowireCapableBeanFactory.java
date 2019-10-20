package com.smartspring.beans.factory.config;

import com.smartspring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}
