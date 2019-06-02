package za.framework.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.OptimisticLockingFailureException;

public class RetryOnOptimisticLockingFailureAspect {

    private static final int maxRetries = 2;

    @Pointcut("@annotation(RetryOnOptimisticLockingFailure)")
    public void retryOnOptFailure() {
    }

    @Around("retryOnOptFailure()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (OptimisticLockingFailureException ex) {
                if (numAttempts > maxRetries) {
                    //log failure information, and throw exception
                    throw ex;
                } else {
                    //log failure information for audit/reference
                    //will try recovery
                }
            }
        } while (numAttempts <= this.maxRetries);
        return null;
    }
}
