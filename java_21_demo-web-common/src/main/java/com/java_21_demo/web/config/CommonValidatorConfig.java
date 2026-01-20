package com.java_21_demo.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Configuration
@ConditionalOnProperty(name = "validator.fail-fast", havingValue = "true")
public class CommonValidatorConfig {

    @Bean
    public Validator validator() {
        // Validation.byProvider(HibernateValidator.class);
        return Validation.byDefaultProvider()
            .configure()
            .addProperty("hibernate.validator.fail_fast", "true")
            .buildValidatorFactory()
            .getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());

        return methodValidationPostProcessor;
    }
}
