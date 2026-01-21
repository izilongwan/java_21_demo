package com.java_21_demo.feign.config;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FeignDefaultConfig implements EnvironmentPostProcessor {

    private static final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = new ClassPathResource("feign-default-common.yml");
        System.out.println("Checking for feign-default-common.yml: " + resource.exists());
        if (resource.exists()) {
            try {
                PropertySource<?> propertySource = loader.load("feign-default-common-config", resource).get(0);
                environment.getPropertySources().addFirst(propertySource);
                System.out.println("Successfully loaded feign-default-common.yml into environment");
            } catch (IOException ex) {
                System.err.println("Failed to load feign-default-common.yml: " + ex.getMessage());
                throw new IllegalStateException("Failed to load local feign configuration", ex);
            }
        }
    }

}
