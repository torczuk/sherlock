package com.github.torczuk.sherlock.util.matcher

import com.github.torczuk.sherlock.domain.command.model.WebPage
import org.mockito.ArgumentMatcher
import reactor.bus.Event


class HasTheSameWebPageAs extends ArgumentMatcher<Event<WebPage>> {
    WebPage webPage

    HasTheSameWebPageAs(WebPage webPage) {
        this.webPage = webPage
    }

    @Override
    boolean matches(Object argument) {
        Event<WebPage> event = argument
        WebPage eventWebPage = event.data
        return webPage == eventWebPage
    }
}
