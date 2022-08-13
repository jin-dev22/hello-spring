package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class StopWatchAspect {
	@Pointcut("execution(* com.kh.spring.todo.contorller.TodoController.insertTodo(..))")
	public void pointcut() {}
	
	 @Around("pointcut()")
	 public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable{
		 
		 StopWatch stopWatch = new StopWatch();
		 stopWatch.start();
		 
		 Object obj = joinPoint.proceed();
		 stopWatch.stop();
		 
		 long millis = stopWatch.getTotalTimeMillis();
		 log.debug("insertTodo 소요시간 : {}ms", millis);
		 
		 return obj;
	 }
}
