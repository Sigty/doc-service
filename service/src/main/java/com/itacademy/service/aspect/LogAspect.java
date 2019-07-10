package com.itacademy.service.aspect;

import java.util.Arrays;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Log4j
@Aspect
public class LogAspect {

    @Pointcut("execution(public * com.itacademy.service.service.*.*(..))")
    public void isServiceLayer() {
    }

    @Around("isServiceLayer()")
    public Object aroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String nameMethod = joinPoint.getSignature().getName();
        String argsMethodArray = Arrays.toString(joinPoint.getArgs());
        log.info("Class - " + className + ", Method - " + nameMethod + ", args: " + argsMethodArray + " = begin");
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            log.info("Class - " + className + ", Method - " + nameMethod + ", args: " + argsMethodArray + " = Exception");
            throw ex;
        }
        log.info("Class - " + className + ", Method - " + nameMethod + ", args: " + argsMethodArray + " = done");
        return result;
    }
}