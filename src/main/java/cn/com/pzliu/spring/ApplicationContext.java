package cn.com.pzliu.spring;

import cn.com.pzliu.spring.annotation.Component;
import cn.com.pzliu.spring.annotation.ComponentScan;
import cn.com.pzliu.spring.annotation.Scope;
import cn.com.pzliu.spring.exception.CreateBeanException;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Naive
 * @date 2022-04-16 16:06
 */
public class ApplicationContext {

    //1、扫描->BeanDefinition->beanDefinitionMap
    //2、实例化单例Bean->singletonBeanMap
    //3、根据信息获取or创建Bean

    private Class configClass;


    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> singletonBeanMap = new ConcurrentHashMap<>();

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            // 包路径，扫描的是.class文集哦,相对路径
            String scanPackagePath = componentScan.value();
            scanPackagePath = scanPackagePath.replace(".","/");
            // 获取class文件的路径
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            // 获取到class的路径
            URL scanPath = classLoader.getResource(scanPackagePath);
            File classFileRootFile = new File(scanPath.getFile());
            System.out.println(classFileRootFile);
            if (classFileRootFile.isDirectory()){
                File[] classFiles = classFileRootFile.listFiles();
                for (File classFile : classFiles) {
                    String fileAbsolutePath = classFile.getAbsolutePath();
                    System.out.println(fileAbsolutePath);
                    if (fileAbsolutePath.endsWith(".class")){
                        // 反射,全限定类名
                        String className = getBeanClassName(fileAbsolutePath,scanPackagePath);
                        Class<?> clazz = null;
                        try {
                            clazz = classLoader.loadClass(className);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (clazz.isAnnotationPresent(Component.class)){
                            // Bean
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if ("".equals(beanName)){
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                            //此处应该只生成BeanDefinition对象,原因
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setType(clazz);
                            if (clazz.isAnnotationPresent(Scope.class)){
                                Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                String scopeValue = scopeAnnotation.value();
                                beanDefinition.setScope(scopeValue);
                            }else {
                                beanDefinition.setScope("singleton");
                            }
                            // 存储起来
                            beanDefinitionMap.put(beanName,beanDefinition);
                        }

                    }
                }

            }

        }
        //创建单例Bean
        beanDefinitionMap.forEach((name,entity)->{
            if ("singleton".equals(entity.getScope())){
                Object bean = createBean(name,entity);
                singletonBeanMap.put(name,bean);
            }
        });

    }

    private String getBeanClassName(String fileAbsolutePath, String scanPackagePath) {
        int start = fileAbsolutePath.indexOf(scanPackagePath.replace("/","\\"));
        int end = fileAbsolutePath.indexOf(".class");
        String packagePath = fileAbsolutePath.substring(start, end);
        return packagePath.replace("\\", ".");
    }

    private Object createBean(String name, BeanDefinition beanDefinition) {
        Class beanType = beanDefinition.getType();
        try {
            Object instance = beanType.getConstructor().newInstance();
            return instance;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new CreateBeanException("创建Bean异常",e);
        }
    }


    public Object getBean(String beanName) {
        //通过BeanDefinition对象生成Bean
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null){
            throw new NullPointerException();
        }else {
            String scope = beanDefinition.getScope();
            if ("singleton".equals(scope)){
                //单例
                Object bean = singletonBeanMap.get(beanName);
                if (bean == null){
                    Object newBean = createBean(beanName, beanDefinition);
                    singletonBeanMap.put(beanName,newBean);
                }
                return bean;
            }else {
                // 多例 要多次创建
                return createBean(beanName,beanDefinition);
            }
        }
    }
}