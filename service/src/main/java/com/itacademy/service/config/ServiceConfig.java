package com.itacademy.service.config;

import com.itacademy.database.config.CachingConfig;
import com.itacademy.database.config.DatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.itacademy.service")
@Import({DatabaseConfig.class, CachingConfig.class})
public class ServiceConfig {
}