package com.smartspring.Context.annotation;

import com.smartspring.beans.factory.annotation.AnnotatedBeanDefinition;
import com.smartspring.beans.factory.support.GenericBeanDefinition;
import com.smartspring.core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}