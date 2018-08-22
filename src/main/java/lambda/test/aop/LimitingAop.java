package lambda.test.aop;

import com.google.common.util.concurrent.RateLimiter;
import lambda.test.customize.Limiting;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 使用aop 环绕通知 判断拦截所以spirngmvc 请求 ，判断请求方法上是否存在Limiting 注解
 */
//定义切换
@Aspect
//注入spirng 容器
@Component
public class LimitingAop {
    private Map<String, RateLimiter> limitingMap = new ConcurrentHashMap<>();

    // 申明一个切点 里面是 execution表达式
    @Pointcut("execution(public * lambda.test.controller.*.*(..))")
    private void controllerAspect() {
    }

    // aop 环绕通知
    @Around(value = "controllerAspect()")
    public Object methodBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1判断请求方法是否存在注解
        Method method = getMethod(proceedingJoinPoint);
        if (method == null) {
            //直接报错
            return null;
        }
        //2使用java 反射机制 获取拦截方法上注解的参数
        Limiting limiting = method.getDeclaredAnnotation(Limiting.class);
        if (limiting == null) {
            //proceedingJoinPoint.proceed() 直接调用进入实际请求方法中
            return proceedingJoinPoint.proceed();
        }
        double premitsPerSecond = limiting.premitsPerSecond();
        long timeout = limiting.timeout();
        //3调用原生RateLimiter创建令牌
        //获得request 对象
        HttpServletRequest request = getRequest();
        RateLimiter rateLimiter = null;
        //判断语句  获得request 请求的url参数
        if (limitingMap.containsKey(request.getRequestURI())) {
            rateLimiter = limitingMap.get(request.getRequestURI());
        } else {
            rateLimiter = RateLimiter.create(premitsPerSecond);
            limitingMap.put(request.getRequestURI(), rateLimiter);
        }
        //4获取令牌桶中的令牌，如果没有有效期令牌的话，则直接调用本地服务降级方法，不会进入到实际请求方法中
        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.MICROSECONDS);
        if (!tryAcquire) {
            //走服务降级
            fallback();
            return null;
        }
        //5 如果在有效期 获得 则进入请求方法中
        return proceedingJoinPoint.proceed();
    }


    //服务降级
    private void fallback() throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //生成 httpServletRequest对象获得url
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-type","text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        try {
            printWriter.print("一直等待!");
        } catch (Exception e) {

        }
    }


        private HttpServletRequest getRequest() {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            //生成 httpServletRequest对象获得url
            return attributes.getRequest();
        }

        private Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
            //获取到目标代理对象
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //获取到AOP拦截的方法
            Method method = signature.getMethod();
            return method;
    }
}
