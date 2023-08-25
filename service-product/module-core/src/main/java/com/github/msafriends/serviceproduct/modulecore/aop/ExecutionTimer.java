package com.github.msafriends.serviceproduct.modulecore.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {

    @Pointcut("@annotation(com.github.msafriends.serviceproduct.modulecore.aop.annotation.ExeTimer)")
    private void timer(){};

    @Around("timer()")
    public void assumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        joinPoint.proceed();
        stopWatch.stop();
        long spanTime = stopWatch.getTotalTimeMillis();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        log.info("메소드 이름 : {}, 실행 시간 {}ms", methodName, spanTime);
    }
}
