package com.github.torczuk.sherlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import reactor.Environment;
import reactor.bus.EventBus;

@SpringBootApplication
@PropertySource(value = {"classpath:application.properties"})
public class Sherlock {
    private static final Logger logger = LoggerFactory.getLogger(Sherlock.class);

    @Bean Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    public static void main(String[] args) {
        SpringApplication.run(Sherlock.class, args);
        logger.info("Application started ...");
    }
}