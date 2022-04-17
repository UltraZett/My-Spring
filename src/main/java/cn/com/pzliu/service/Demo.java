package cn.com.pzliu.service;

import cn.com.pzliu.spring.ApplicationContext;

/**
 * @author Naive
 * @date 2022-04-16 16:04
 */
public class Demo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Config.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        System.out.println(userService);
        userService.testAutowired();
    }

}
