package com.github.torczuk.sherlock.infrastructure.eventbus;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LuceneIndexerBuilderConsumer implements Consumer<Event<WebPage>> {
    private static final Logger logger = getLogger(LuceneIndexerBuilderConsumer.class);

    @Override public void accept(Event<WebPage> event) {
        WebPage webPage = event.getData();
        logger.info("Processing url {}", webPage.url());
    }
}