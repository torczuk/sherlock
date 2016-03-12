package com.github.torczuk.sherlock.infrastructure.eventbus.consumer;

import com.github.torczuk.sherlock.domain.command.model.Content;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.repository.WebPageWriteRepository;
import com.github.torczuk.sherlock.domain.command.service.ContentParser;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class WebPageStorageConsumer implements Consumer<Event<WebPage>> {
    private static final Logger logger = getLogger(WebPageStorageConsumer.class);

    private final WebPageWriteRepository webPageWriteRepository;
    private final ContentParser contentParser;

    @Autowired
    public WebPageStorageConsumer(WebPageWriteRepository webPageWriteRepository, ContentParser contentParser) {
        this.webPageWriteRepository = webPageWriteRepository;
        this.contentParser = contentParser;
    }


    @Override public void accept(Event<WebPage> event) {
        WebPage webPage = event.getData();
        logger.info("Processing url {}", webPage.url());

        Optional<Content> parsed = webPage.content()
                .map(content -> contentParser.parse(content))
                .map(text -> new Content(text));
        WebPage plainTextWebPage = new WebPage(webPage.url(), parsed);

        webPageWriteRepository.write(plainTextWebPage);
    }
}