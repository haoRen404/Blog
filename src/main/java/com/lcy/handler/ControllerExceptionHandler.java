package com.lcy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截所有的异常，统一处理
 */
@ControllerAdvice // @ControllerAdvice 拦截异常并统一处理
public class ControllerExceptionHandler {

    // 记录日志的对象，注意导入的包是org.slf4j.Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)  // @ExceptionHandler：统一处理某一类异常
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        // 记录异常信息
        logger.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

        // // 这里把所有异常都拦截了，但是404、500需要另外处理，这个if会把400和500异常交给spring进行处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error"); // 设置返回到哪个页面
        return mv;
    }

}
