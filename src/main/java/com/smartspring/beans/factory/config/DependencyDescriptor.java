package com.smartspring.beans.factory.config;

import com.smartspring.util.Assert;

import java.lang.reflect.Field;

public class DependencyDescriptor {

    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }
        //目前只支持字段自动注入，不支持构造方法，setter方法自动注入
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
