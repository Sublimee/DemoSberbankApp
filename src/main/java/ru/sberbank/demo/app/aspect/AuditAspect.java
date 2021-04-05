package ru.sberbank.demo.app.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Before("execution(* ru.sberbank.demo.app.repository.AccountRepository.getAccountsByClientId(Long))")
    public void auditClientAccounts(final JoinPoint joinPoint) {
        logger.info("Searching Project with id {}", joinPoint.getArgs()[0]);
    }

}
