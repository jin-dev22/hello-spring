package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Advice의 다섯가지타입
 * 
 * 1. Around Advice
 * 2. Before Advice
 * 3. After Advice
 * 4. AfterReturning Advice
 * 5. AfterThrowing Advice
 * 
 *
 */
//@Component
//@Aspect//실행 ㄴㄴ, 참고용으로 보라고 작성된 클래스임.
@Slf4j
public class AdviceSignature {
	
	@Pointcut("execution(* com.kh.spring.common.aop..*Impl.*(..))")
	public void pointcut() {}
	
	@Before("pointcut()")
	public void beforeAdvice(JoinPoint joinPoint) {
		//타겟메소드 실행전 코드
	}
	
	@Around("pointcut()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		//Target메소드 실행전 코드 
		
		Object obj = joinPoint.proceed();
		
		//Target메소드 실행후 코드 
		
		return obj;
	}
	
	@AfterReturning(pointcut="pointcut()", returning="returnObj")
	public void afterReturningAdvice(JoinPoint joinPoint, Object returnObj) {
		//타겟메소드의 리턴 데이터를 다른 용도로 처리할 때 사용함.
		log.debug("리턴값  = "+ returnObj);
	}
	
	@After("pointcut()")
	public void afterAdvice() {
		//타겟메소드의 예외 발생 여부에 상관없이 무조건 수행되는 advice
	}
	
	@AfterThrowing(pointcut="pointcut()", throwing="exceptObj")
	public void afterThrowingAdvice(JoinPoint jp, Exception exceptObj) {
		//타겟메소드 실행중 예외가 발생했을 때, 부가적인 로직을 제공할 목적으로 사용하는 Advice. 예외처리아님!!
		String methodName = jp.getSignature().getName();
		log.debug(methodName + "() 메소드 수행 중 예외 발생!");
		
		if(exceptObj instanceof IllegalArgumentException) {
			log.debug("IllegalArgumentException : 부적합한 값이 입력되었습니다.");
		}else if(exceptObj instanceof NumberFormatException) {
			log.debug("NumberFormatException : 숫자 형식의 값이 아닙니다.");
		}else if(exceptObj instanceof Exception) {
			log.debug("Exception : "+exceptObj.getMessage());
		}else {
			log.debug(exceptObj.getMessage());
		}
	}
}
