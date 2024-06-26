package com.paololauria.bnb.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "http://localhost:8080", "http://localhost:8100")
                .allowedMethods("GET", "POST", "OPTIONS","DELETE","PUT")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}