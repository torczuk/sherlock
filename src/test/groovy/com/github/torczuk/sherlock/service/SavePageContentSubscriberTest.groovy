package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.model.Content
import com.github.torczuk.sherlock.domain.model.WebPage
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class SavePageContentSubscriberTest extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()
    @Subject SavePageContentSubscriber savePageContentSubscriber

    def setup() {
        savePageContentSubscriber = new SavePageContentSubscriber(temporaryFolder.root.absolutePath)
    }

    def 'save content page on disc under specified directory' () {
        given:
        WebPage webPage = new WebPage('http://example.com', new Content('any content'))

        when:
        savePageContentSubscriber.save(webPage)

        then:
        'any content' == new File(temporaryFolder.root.absolutePath, "http___example.com").text
    }

}
