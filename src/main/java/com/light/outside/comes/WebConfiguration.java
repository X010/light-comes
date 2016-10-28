package com.light.outside.comes;

import com.light.outside.comes.filter.SessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public SessionFilter sessionFilter() {
        return new SessionFilter();
    }

}
