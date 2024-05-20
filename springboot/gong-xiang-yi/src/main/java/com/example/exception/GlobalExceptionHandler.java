package com.example.exception;

import com.example.pojo.Result;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)

    public Result HandlerException(Exception e) {
        e.printStackTrace();
        String msg = e.getMessage();

        System.out.println(msg);

        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(msg);

        while(matcher.find()) {
            msg = matcher.group(0);
        }

        System.out.println("-------------");
        System.out.println(msg);
        System.out.println("-------------");
        return Result.error(StringUtils.isNullOrEmpty(e.getMessage()) ? "操作失败" : msg);
    }
}