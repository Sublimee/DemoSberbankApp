package ru.sberbank.demo.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @Before("execution(* ru.sberbank.demo.app.repository.AccountsRepository.getAccountsByClientId(Long))")
    public void auditClientAccounts(final JoinPoint joinPoint) {
        log.info("Searching Project with id {}", joinPoint.getArgs()[0]);
    }

}
