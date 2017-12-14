package com.khabaj.ekpir.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AppLogger {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.khabaj.ekpir..*.*(..))")
    private void basePackageMethods() {
    }

    @Around("execution(* com.khabaj.ekpir..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        logger.debug("Call " + className + "." + methodName);

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        logger.debug("Exit " + className + "." + methodName + " [" + stopWatch.getTotalTimeMillis() + " ms]");

        return result;
    }
}
