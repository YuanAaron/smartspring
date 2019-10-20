package com.smartspring.beans.factory.support;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.ConstructorArgument;
import com.smartspring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {
    private String id; //对应xml中的id
    private String beanClassName; //对应xml中的class
    private Class<?> beanClass;
    private boolean singleton=true;
    private boolean prototype=false;
    private String scope=SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues=new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();

    public GenericBeanDefinition() {

    }

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id=id;
        this.beanClassName=beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setBeanClassName(String className){
        this.beanClassName = className;
    }

    @Override
    public boolean hasBeanClass(){
        return this.beanClass != null;
    }

    //该方法必须要在getBeanClass方法前才可以
    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException{
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        if(this.beanClass == null){
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public String getID() {
        return this.id;
    }

    public void setId(String id) {
        this.id=id;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
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
