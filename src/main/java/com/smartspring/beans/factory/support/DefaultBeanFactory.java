package com.smartspring.beans.factory.support;

import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.PropertyValue;
import com.smartspring.beans.SimpleTypeConverter;
import com.smartspring.beans.TypeConverter;
import com.smartspring.beans.factory.BeanCreationException;
import com.smartspring.beans.factory.BeanFactory;
import com.smartspring.beans.factory.config.ConfigurableBeanFactory;
import com.smartspring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory,BeanDefinitionRegistry{

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private ClassLoader beanClassLoader=null;

    @Override
    public void registryBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId,bd);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    //从BeanDefinition变成Bean的实例
    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if(bd == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (bd.isSingleton()) {
            Object bean=this.getSingleton(beanId);
            if (bean==null) {
                bean=createBean(bd);
                this.registerSingleton(beanId,bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd) {
        //实例化Bean
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd,bean);
        return bean;
    }
    
    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance(); // 前提：beanClassName类需要有无参构造函数
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    protected void populateBean(BeanDefinition bd, Object bean){
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        try{
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                TypeConverter converter = new SimpleTypeConverter();

                //假设originalValue表示的是ref="accountDao",那么resolvedValue表示已经通过resolver得到的accountDao的实例。
                //接下来如何调用v2中PetStoreService的setAccountDao方法将resolvedValue赋值给accountDao属性呢？
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)){
                        Object converteredValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, converteredValue);
                        break;
                    }
                }
            }
        }catch(Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader=beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader!=null?this.beanClassLoader:ClassUtils.getDefaultClassLoader();
    }
}
