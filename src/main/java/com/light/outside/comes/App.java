package com.light.outside.comes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 主程序入口
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource("applicationContext.xml")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
