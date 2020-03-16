package ru.sberbank.demo.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);

    @Before("execution(* ru.sberbank.demo.app.repository.AccountsRepository.getAccountsByClientId(Long))")
    public void auditClientAccounts(final JoinPoint joinPoint) {
        LOGGER.info("Searching Project with id {}", joinPoint.getArgs()[0]);
    }

}
