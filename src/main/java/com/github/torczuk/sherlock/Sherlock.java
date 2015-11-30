package com.github.torczuk.sherlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:application.properties"})
public class Sherlock {
    private static final Logger logger = LoggerFactory.getLogger(Sherlock.class);

    public static void main(String[] args) {
        SpringApplication.run(Sherlock.class, args);
        logger.info("Application started ...");
    }
}