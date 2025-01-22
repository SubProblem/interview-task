package com.task.processingapp.config;

import com.task.processingapp.dto.ApiResponseDto;
import com.task.processingapp.dto.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("@annotation(Timed)")
    public Object measureProcessingTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        double duration = (double) (System.nanoTime() - startTime) / 1_000_000_000;

        log.info("{} executed in {} ms", joinPoint.getSignature(), duration);

        return switch (result) {
            case ProcessResult processResult -> new ProcessResult(duration);
            case ApiResponseDto responseDto -> responseDto.addTotalProcessingTime(duration);
            default -> result;
        };
    }
}
