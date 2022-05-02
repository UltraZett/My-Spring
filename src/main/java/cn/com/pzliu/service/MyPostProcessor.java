package cn.com.pzliu.service;

import cn.com.pzliu.spring.BeanPostProcessor;
import cn.com.pzliu.spring.annotation.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Naive
 * @date 2022-04-17 12:18
 */
@Component
public class MyPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")){
            System.out.println("我可以在postProcessBeforeInitialization操作Bean对象了");
            System.out.println(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")){
            System.out.println("我可以在postProcessAfterInitialization操作Bean对象了");
            System.out.println(bean);
            // 来返回一个代理对象
            Object proxyInstance = Proxy.newProxyInstance(UserService.class.getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                System.out.println("切面逻辑");
                return method.invoke(bean,args);
            });

            return proxyInstance;
        }


        return bean;
    }
}
