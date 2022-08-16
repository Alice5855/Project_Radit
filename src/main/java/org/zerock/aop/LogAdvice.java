package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

// 로그를 기록하는 것은 '반복적이면서 핵심 로직도 아니며, 필요하기는 한' 기능이기 때문에 '관심사'로
// 간주할 수 있음. AOP 개념에서 Advice는 '관심사'를 코드로 구현한 것
// Page 461 : AOP를 적용하면 작성하지 않은 code가 다른 code와 결합되어서 동작하기 때문에
// Scenario에 따른 여러가지 적용을 달리 할 수 있다
@Aspect // 관심사(Concern, Aspect, 관점) 영역 관리
@Log4j
@Component // Spring에서 Bean으로 인식시키기 위하여 사용됨
public class LogAdvice {
	
	/* @Before : Before Advice(concern을 분리해 놓은 code)를 구현한 method에 추가함
	 * Target의 JoinPoint(Target이 가진 여러 method)를 호출하기 전에 실행되는 코드
	 * execution(*[접근제한자] Package.class *[class].*[method](params))
	 * @Before("execution(pointcut)") : method를 기준으로 
	 * Pointcut(관심사와 비즈니스 로직이 결합되는 지점을 결정)
	 */
	@Before("execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("=========================================");
	}
	
	/* Pointcut에 doAdd() method를 명시하고 param type을 지정한다. args(...)
	 * 에서는 변수 명을 지정하여 명시된 method를 명시된 args로 실행하게 한다. doAdd()
	 * method가 실행될 때에 logBeforeWithParam이 자동으로 log표시를 해준다.
	 */
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	// Page 460 : AOP의 @AfterThrowing은 지정된 대상이 예외를 발생한 후에 log를 출력
	/* 'pointcut'과 'throwing' 속성을 갖는다.
	 */
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("=== Error Occurred ===");
		log.info("=== Actual Error : " + exception + " ===");
	}
	
	// Page 461 : @Around는 직접 대상 method를 실행할 수 있는 권한을 가지고 before,
	// after를 처리함. ProceedingJoinPoint는 @Around와 함께 사용하여 param이나
	// exception을 처리할 수 있게 함
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target : " + pjp.getTarget());
		log.info("Param : " + Arrays.toString(pjp.getArgs()));
		
		// invoke method
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.info("Time : " + (end - start));
		
		return result;
	}
	/* @Around method는 (void가 아닌) return type이 존재해야 하며 실행 결과를 return
	 * 하는 형태로 작성되어야 한다.
	 * ProceedingJoinPoint는 AOP의 대상이 되는 target이나 parameter를 파악하고, 직접
	 * 실행을 결정할 수도 있다. 이를 invoke method라 하는데, target을 parameter로 설정
	 * 하여 대상이 되는 method를 call하는 것.
	 * INFO : org.zerock.aop.LogAdvice - Target : 
	 * org.zerock.service.SampleServiceImpl@435fb7b5
	 * INFO : org.zerock.aop.LogAdvice - Param : [416, 671]
	 * INFO : org.zerock.aop.LogAdvice - =========================================
	 * log가 before 이전에 출력됨을 확인
	 */
}
