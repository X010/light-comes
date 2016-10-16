package com.light.outside.comes;

import com.light.outside.comes.filter.SessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Configuration
public class WebConfiguration {

    @Bean
    public SessionFilter sessionFilter() {
        return new SessionFilter();
    }

}
