package com.github.torczuk.sherlock.infrastructure.eventbus;

import com.github.torczuk.sherlock.infrastructure.eventbus.consumer.LuceneIndexerBuilderConsumer;
import com.github.torczuk.sherlock.infrastructure.eventbus.consumer.WebPageStorageConsumer;
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
    private final WebPageStorageConsumer webPageStorageConsumer;

    @Autowired
    public EventBusConfiguration(EventBus eventBus, LuceneIndexerBuilderConsumer luceneIndexerBuilderConsumer, WebPageStorageConsumer webPageStorageConsumer) {
        this.eventBus = eventBus;
        this.luceneIndexerBuilderConsumer = luceneIndexerBuilderConsumer;
        this.webPageStorageConsumer = webPageStorageConsumer;
    }

    @PostConstruct
    public void registerEventBusConsumers() {
        logger.info("Configuring event bus ...");
        eventBus.on($("webPage"), luceneIndexerBuilderConsumer);
        eventBus.on($("webPage"), webPageStorageConsumer);
    }
}
