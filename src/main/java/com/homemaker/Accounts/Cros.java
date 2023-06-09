package com.homemaker.Accounts;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class Cros implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.print("Cros addCorsMappings() enabled by sunil");
        registry.addMapping("/**")
                .allowedOrigins("*"); // '*' will allow all URLS
                //.allowedOrigins("http://localhost:3000"); //Specific URL's.
    }
}

