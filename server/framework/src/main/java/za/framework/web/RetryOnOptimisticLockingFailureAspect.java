package za.framework.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.OptimisticLockingFailureException;

@Slf4j
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
                    log.error(ex.toString());
                    throw ex;
                } else {
                    log.error("doConcurrentOperation:" + pjp.getSignature().getName() + pjp.getArgs());
                    //will try recovery
                }
            }
        } while (numAttempts <= this.maxRetries);
        return null;
    }
}
