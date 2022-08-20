package client.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ContactValidationAspect {

    @Before("execution(void client.Application.Validation.validateAddContactDetails())")
    public void beforeLogger() {
        System.out.println("Test Aspect log.");
    }
}
