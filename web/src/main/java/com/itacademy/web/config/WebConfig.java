package com.itacademy.web.config;

import com.itacademy.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.itacademy.web")
@Import(ServiceConfig.class)
@EnableTransactionManagement
public class WebConfig {
}