package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {
	@Pointcut("execution(* com.kh.spring.todo..*(..))")//todo패키지 하위의 모든 클래스의 모든 메소드, 매개변수도 타입/개수 상관없음으로 설정
	public void pointcut() {}
	@Around("pointcut()")
	public Object logAdviece(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();//실행할 타입. joinPoint의 메소드 시그니처
		String typeName = signature.getDeclaringTypeName();//클래스명
		String methodName = signature.getName();//메소드 이름
		
		//Before
		log.debug("{}.{}호출전", typeName, methodName);
		
		Object obj = joinPoint.proceed();//주업무코드
		//After
		log.debug("{}.{}호출후", typeName, methodName);
		
		
		return obj;
	}
}
