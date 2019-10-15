package com.smartspring.beans.factory.support;

import com.smartspring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {
    private String id; //对应xml中的id
    private String beanClassName; //对应xml中的class
    private boolean singleton=true;
    private boolean prototype=false;
    private String scope=SCOPE_DEFAULT;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id=id;
        this.beanClassName=beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope=scope;
        this.singleton=SCOPE_SINGLETON.equals(this.scope) || SCOPE_DEFAULT.equals(this.scope);
        this.prototype=SCOPE_PROTOTYPE.equals(this.scope);
    }
}
