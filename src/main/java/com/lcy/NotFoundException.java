package com.lcy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// 自定义异常信息类，用于元素没找到时，如博客没找到、标签没找到
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    // 提示信息
    public NotFoundException(String message) {
        super(message);
    }

    // 可以抛出
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
