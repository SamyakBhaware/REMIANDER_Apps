package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("LoginForm");
        registry.addViewController("/register").setViewName("RegistrationForm");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/preview").setViewName("Preview");
        registry.addViewController("/authenticatedPreview").setViewName("AuthenticatedPreview");

    }
}
