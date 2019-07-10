package com.itacademy.service.config;

import com.itacademy.service.aspect.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.itacademy.service")
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}
