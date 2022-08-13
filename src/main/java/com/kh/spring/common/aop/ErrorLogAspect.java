package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ErrorLogAspect {

//	@Pointcut("within(com.kh.spring.*.controller.*)")
	@Pointcut("execution(* com.kh.spring..*Controller.*(..))")
	public void pc() {}
	
	@AfterThrowing(pointcut = "pc()", throwing = "e")
	public void errorLogAdvice(JoinPoint jp, Exception e) {//JoinPoint빼먹으면 안됨
		System.out.println(11111111);
		log.error(e.getMessage(), e);
	}
}
