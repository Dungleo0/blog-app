package com.example.blogapp.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperComponent {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
