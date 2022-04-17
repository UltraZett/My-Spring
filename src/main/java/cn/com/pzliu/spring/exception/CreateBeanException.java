package cn.com.pzliu.spring.exception;

/**
 * @author Naive
 * @date 2022-04-17 10:39
 */
public class CreateBeanException extends RuntimeException {
    public CreateBeanException() {
    }

    public CreateBeanException(String message) {
        super(message);
    }

    public CreateBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateBeanException(Throwable cause) {
        super(cause);
    }

    public CreateBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
