package com.itacademy.web.config;

import com.itacademy.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.itacademy.web")
@Import(ServiceConfig.class)
public class WebConfig {
}