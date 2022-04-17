package cn.com.pzliu.service;

import cn.com.pzliu.spring.annotation.Autowired;
import cn.com.pzliu.spring.annotation.Component;
import cn.com.pzliu.spring.annotation.Scope;

/**
 * @author Naive
 * @date 2022-04-16 16:04
 */
@Component
@Scope("")
public class UserService {

    @Autowired
    private OrderService orderService;


    public void testAutowired(){
        System.out.println(orderService);
    }

}
