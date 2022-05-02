package cn.com.pzliu.spring;

/**
 * @author Naive
 * @date 2022-04-17 12:16
 */
public interface BeanPostProcessor {

     Object postProcessBeforeInitialization(String beanName,Object bean);
     Object postProcessAfterInitialization(String beanName,Object bean);

}
