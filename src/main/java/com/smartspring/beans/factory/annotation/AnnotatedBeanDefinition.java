package com.smartspring.beans.factory.annotation;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
