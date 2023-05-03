package org.automotive.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.automotive"})
public class ApplicationConfiguration {

    @Bean
    ObjectMapper pureObjectMapper() {
        return new ObjectMapper();
    }

}
