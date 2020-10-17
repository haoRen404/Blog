package com.lcy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

// 用于日志记录

@Aspect     // @Aspect(切面): 通常是一个类，里面可以定义切入点和通知
@Component  // @controller: controller控制器层（注入服务）
public class LogAspect {
    // 日志记录器对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // // 定义切面
    @Pointcut("execution(* com.lcy.web.*.*(..))")   // // 定义切面，切入点
    public void log() {}


    @Before("log()")    // @Before： 标识一个前置增强方法，相当于BeforeAdvice的功能
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();//获取servlet请求信息对象
        String url = request.getRequestURL().toString();//获取url
        String ip = request.getRemoteAddr();// 获取ip
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();// 拿到请求的参数
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()") // @After： final增强，不管是抛出异常或者正常退出都会执行.
    public void doAfter() {

    }

    @AfterReturning(returning = "result",pointcut = "log()")    // @AfterReturning：  后置增强，似于AfterReturningAdvice, 方法正常退出时执行.
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    // 内部类
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
