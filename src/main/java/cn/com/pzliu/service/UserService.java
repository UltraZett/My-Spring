package cn.com.pzliu.service;

import cn.com.pzliu.spring.BeanNameAware;
import cn.com.pzliu.spring.InitializingBean;
import cn.com.pzliu.spring.annotation.Autowired;
import cn.com.pzliu.spring.annotation.Component;
import cn.com.pzliu.spring.annotation.Scope;

/**
 * @author Naive
 * @date 2022-04-16 16:04
 */
@Component
@Scope("")
public class UserService implements BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String userName;

    public void testAutowired(){
        System.out.println(orderService);
    }



    /**
     * 由Spring进行调用
     * @param beanName
     */
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("doSomething");
    }
}
