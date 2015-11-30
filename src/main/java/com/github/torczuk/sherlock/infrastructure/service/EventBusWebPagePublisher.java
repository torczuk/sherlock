package com.github.torczuk.sherlock.infrastructure.service;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.service.WebPagePublisher;
import org.springframework.stereotype.Service;

@Service
public class EventBusWebPagePublisher implements WebPagePublisher {

    @Override public void publish(WebPage webPage) {
    }
}
