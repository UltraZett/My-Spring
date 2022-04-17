package cn.com.pzliu.spring;

/**
 * @author Naive
 * @date 2022-04-17 11:45
 */
public interface BeanNameAware {

    /**
     * 设置Bean的beanName
     * @param beanName
     */
    void setBeanName(String beanName);

}
