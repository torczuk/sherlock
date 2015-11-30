package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.WebPage;

public interface WebPagePublisher {
    void publish(WebPage webPage);
}
