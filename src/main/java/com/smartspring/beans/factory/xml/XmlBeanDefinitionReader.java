package com.smartspring.beans.factory.xml;

import com.smartspring.Context.annotation.ClassPathBeanDefinitionScanner;
import com.smartspring.beans.BeanDefinition;
import com.smartspring.beans.ConstructorArgument.ValueHolder;
import com.smartspring.beans.PropertyValue;
import com.smartspring.beans.factory.BeanDefinitionStoreException;
import com.smartspring.beans.factory.config.RuntimeBeanReference;
import com.smartspring.beans.factory.config.TypedStringValue;
import com.smartspring.beans.factory.support.BeanDefinitionRegistry;
import com.smartspring.beans.factory.support.GenericBeanDefinition;
import com.smartspring.core.io.Resource;
import com.smartspring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {

    private final Log logger= LogFactory.getLog(getClass());

    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type"; //我们目前用不到，加上将来方便扩展
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;
        try{
            is=resource.getInputStream();

            SAXReader reader = new SAXReader();
            Document doc = reader.read(is); // doc对应XML文件

            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = (Element)iter.next();
                String namespaceUri = ele.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceUri)){
                    parseDefaultElement(ele); //普通的<bean>
                } else if(this.isContextNamespace(namespaceUri)){
                    parseComponentElement(ele); //例如<context:component-scan>
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseDefaultElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE); //解析id
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE); //解析class
        BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
        if (ele.attribute(SCOPE_ATTRIBUTE)!=null) { //解析scope
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        parseConstructorArgElements(ele,bd); //解析多个<constructor-arg>元素
        parsePropertyElement(ele,bd); //解析<property>元素
        this.registry.registryBeanDefinition(id, bd);
    }

    private void parseComponentElement(Element ele) {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    //解析<property>元素
    public void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iter= beanElem.elementIterator(PROPERTY_ELEMENT);
        while(iter.hasNext()){
            Element propElem = (Element)iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object val = parsePropertyValue(propElem, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            bd.getPropertyValues().add(pv);
        }
    }

    //解析<property>/<constructor-arg>元素的value属性
    public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!=null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) !=null);

        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if (hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }
        else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

    //解析多个<constructor-arg>元素
    public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iter.hasNext()){
            Element ele = (Element)iter.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    public void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE); //这个目前用不到
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE); //这个目前用不到
        Object value = parsePropertyValue(ele, bd, null);
        ValueHolder valueHolder = new ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)) { //这个目前用不到
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) { //这个目前用不到
            valueHolder.setName(nameAttr);
        }

        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }
}
