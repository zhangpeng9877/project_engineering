package com.itheima.travel.web.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author 张鹏
 * @date 2020/5/18 12:05
 */
public class BeanFactory {
    private BeanFactory() {
        // 构造私有化，防止通过构造方法，获取方法
    }

    public static Object getBean(String id) {
        try {
            // 通过类加载器，加载beans.xml配置文件，获取io流
            InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
            // 创建SAXReader对象，创建dom4j核心解析器对象
            SAXReader saxReader = new SAXReader();
            // 读取io流，加载xml文档路径，解析生成document对象
            Document document = saxReader.read(inputStream);
            // 编写xpath谓语表达式(根据条件查询)
            String xpath = "// bean[@id='" + id + "']";
            // 查询到对应的bean标签，获取到它的节点，强制转换为Element对象
            Element node = (Element) document.selectSingleNode(xpath);
            // 获取到该标签中的，class属性值，全限定类名
            String className = node.attributeValue("class");
            // 通过反射机制加载到jvm虚拟机中
            Class<?> clazz = Class.forName(className);
            // 创建对象
            Object result = clazz.newInstance();
            return result;
            // return JdkProxyFactory.createLogProxy(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
