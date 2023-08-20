package building.sma.market.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.log4j.Log4j2;

@Aspect
@Log4j2
@Component
public class LoggingAdvice {

    @Pointcut("within(building.sma.market.controller..*) " + "|| within(building.sma.market.service.impl..*) "
	    + "|| within(building.sma.market.repository..*)")
    public void packagePointCut() {
    }

    @Around("packagePointCut()")
    public Object logEntryExit(ProceedingJoinPoint joinPoint) throws Throwable {
	String entryLog = String.format("Entering:[%s.%s()] with argument[s]= [%s]",
		joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
		Arrays.toString(joinPoint.getArgs()));
	log.info(entryLog);
	Object result = joinPoint.proceed();
	String exitLog = String.format("Exiting:[%s.%s()] with result=[%s]",
		joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result);
	log.info(exitLog);
	return result;
    }

    @Around("packagePointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	final StopWatch stopWatch = new StopWatch();
	stopWatch.start();
	Object result = joinPoint.proceed();
	stopWatch.stop();
	String executionLog = String.format("Execution time taken for [%s.%s()] is [%d] ms",
		joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
		stopWatch.getTotalTimeMillis());
	log.debug(executionLog);
	return result;
    }

}
