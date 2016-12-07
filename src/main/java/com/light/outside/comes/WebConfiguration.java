package com.light.outside.comes;

import com.light.outside.comes.filter.SessionFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
public class WebConfiguration extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration{

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = super.freeMarkerConfigurer();
        Map<String, Object> sharedVariables = new HashMap<String,Object>();
        sharedVariables.put("baseUrl", baseUrl);
        configurer.setFreemarkerVariables(sharedVariables);

        return configurer;
    }

    @Bean
    public SessionFilter sessionFilter() {
        return new SessionFilter();
    }

}
