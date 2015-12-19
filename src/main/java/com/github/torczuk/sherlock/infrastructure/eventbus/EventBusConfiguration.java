package com.github.torczuk.sherlock.infrastructure.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.EventBus;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

@Component
public class EventBusConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(EventBusConfiguration.class);

    private final EventBus eventBus;
    private final LuceneIndexerBuilderConsumer luceneIndexerBuilderConsumer;

    @Autowired
    public EventBusConfiguration(EventBus eventBus, LuceneIndexerBuilderConsumer luceneIndexerBuilderConsumer) {
        this.eventBus = eventBus;
        this.luceneIndexerBuilderConsumer = luceneIndexerBuilderConsumer;
    }

    @PostConstruct
    public void registerEventBusConsumers() {
        logger.info("Configuring event bus ...");
        eventBus.on($("webPage"), luceneIndexerBuilderConsumer);
    }
}
