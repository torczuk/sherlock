package com.github.torczuk.sherlock.infrastructure.eventbus.publisher;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.service.WebPagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

@Service
public class EventBusWebPagePublisher implements WebPagePublisher {
    private final EventBus eventBus;

    @Autowired
    public EventBusWebPagePublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override public void publish(WebPage webPage) {
        eventBus.notify("webPage", Event.wrap(webPage));
    }
}
