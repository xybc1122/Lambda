package lambda.test.customize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义服务限流注解
 */
//自定义注解必须加 运行的时候才可以通过反射获取
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiting {
    //以每秒单位固定的速率值往令牌桶添加令牌
    double  premitsPerSecond();
    //如果在500秒没拿到token 走服务降级
    long timeout();
}
