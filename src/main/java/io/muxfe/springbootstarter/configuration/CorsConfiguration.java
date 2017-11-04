package io.muxfe.springbootstarter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@Profile("development")
public class CorsConfiguration extends WebMvcConfigurerAdapter {
  
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.
        addMapping("/**").
        allowedMethods("*").
        allowedHeaders("*").
        allowedOrigins("http://localhost:8080").
        maxAge(24 * 60 * 60).
        allowCredentials(true);
    }
}

