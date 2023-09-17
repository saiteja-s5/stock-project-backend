package building.sma.market.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.log4j.Log4j2;

@Aspect
@Log4j2
@Component
public class LoggingAdvice {

    @Value("${aop.enable-entry-exit-logs}")
    private boolean enableAopLogging;

    @Value("${aop.enable-performance-logs}")
    private boolean enablePerformanceLogging;

    @Pointcut("within(building.sma.market.controller..*) " + "|| within(building.sma.market.service.impl..*) "
	    + "|| within(building.sma.market.repository..*)")
    public void packagePointCut() {
    }

    @Around("packagePointCut()")
    public Object logEntryExit(ProceedingJoinPoint joinPoint) throws Throwable {
	if (enableAopLogging) {
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
	return joinPoint.proceed();
    }

    @Around("packagePointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	if (enablePerformanceLogging) {
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
	return joinPoint.proceed();
    }

}
