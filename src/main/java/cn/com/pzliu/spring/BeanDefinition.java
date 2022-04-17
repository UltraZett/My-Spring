package cn.com.pzliu.spring;

/**
 * Bean的定义信息
 * @author Naive
 * @date 2022-04-16 16:42
 */
public class BeanDefinition {

    private Class type;
    private String scope;


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
