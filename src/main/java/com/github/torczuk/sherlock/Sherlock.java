package com.github.torczuk.sherlock;

import com.github.torczuk.sherlock.domain.command.service.WebPageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
@PropertySource(value={"classpath:application.properties"})
public class Sherlock {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sherlock.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        ctx.getBean(WebPageService.class);

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}