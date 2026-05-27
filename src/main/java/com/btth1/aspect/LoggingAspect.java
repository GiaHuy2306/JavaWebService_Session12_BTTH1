package com.btth1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Bước 1: @Before - Chạy trước các method trong BookController
    @Before("execution(* com.btth1.controller.BookController.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("[AOP @Before] Đang gọi method: {} | Tham số đầu vào: {}", methodName, args);
    }

    // Bước 2: @AfterReturning - Chạy sau khi các method trong BookService hoàn thành và trả về kết quả thành công
    @AfterReturning(
            pointcut = "execution(* com.btth1.service.BookService.*(..))",
            returning = "result"
    )
    public void logAfterService(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("[AOP @AfterReturning] Method thuộc Service thành công: {} | Kết quả trả về: {}", methodName, result);
    }

    // Bước 3: @Around - Đo thời gian thực thi của các method trong BookController
    @Around("execution(* com.btth1.controller.BookController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        // Cho phép method gốc chạy tiếp tục
        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("[AOP @Around] Method: {} thực thi trong: {} ms", methodName, executionTime);

        return result;
    }
}
