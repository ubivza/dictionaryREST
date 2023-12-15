package ru.aleksandr.dictionaryrest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictionaryConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
