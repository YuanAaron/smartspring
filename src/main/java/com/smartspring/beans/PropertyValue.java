package com.smartspring.beans;

public class PropertyValue {
    private final String name;
    private final Object value; //可以是ref和value
    private boolean converted=false;
    private Object convertedValue;

    public PropertyValue(String name,Object value) {
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }

    public synchronized void setConvertedValue(Object value) {
        this.converted=true;
        this.convertedValue = value;
    }

    public synchronized Object getConvertedValue() {
        return convertedValue;
    }
}
