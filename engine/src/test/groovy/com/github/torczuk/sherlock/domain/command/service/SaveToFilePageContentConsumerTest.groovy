package com.github.torczuk.sherlock.domain.command.service

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.domain.command.model.WebPage
import com.github.torczuk.sherlock.infrastructure.eventbus.consumer.SaveToFilePageContentConsumer
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import reactor.bus.Event
import spock.lang.Specification
import spock.lang.Subject

class SaveToFilePageContentConsumerTest extends Specification {

    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder()
    @Subject
    SaveToFilePageContentConsumer savePageContentSubscriber

    def setup() {
        savePageContentSubscriber = new SaveToFilePageContentConsumer(temporaryFolder.root.absolutePath)
    }

    def 'save content page on disc under specified host directory for main page'() {
        given:
        WebPage webPage = new WebPage('http://example.com', Optional.of(new Content('any content')))

        when:
        savePageContentSubscriber.accept(Event.wrap(webPage))

        then:
        'any content' == new File(temporaryFolder.root.absolutePath, "example.com").text
    }

    def 'save content page on disc under specified host directory for any sub page'() {
        given:
        WebPage webPage = new WebPage('http://example.com/sub-page/index.html', Optional.of(new Content('any content')))

        when:
        savePageContentSubscriber.accept(Event.wrap(webPage))

        then:
        'any content' == new File(temporaryFolder.root.absolutePath + "/example.com/sub-page", "index.html").text
    }
}
