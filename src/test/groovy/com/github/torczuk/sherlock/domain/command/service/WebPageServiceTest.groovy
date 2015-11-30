package com.github.torczuk.sherlock.domain.command.service

import com.github.torczuk.sherlock.domain.command.factory.WebPageFactory
import com.github.torczuk.sherlock.domain.command.model.WebPage
import spock.lang.Specification
import spock.lang.Subject

import static com.github.torczuk.sherlock.testutils.Stubs.TEXT_WITHOUT_URLS

class WebPageServiceTest extends Specification {

    static final String WEB_PAGE_URL = 'http://example.com'

    WebPage webPage = new WebPage(WEB_PAGE_URL, TEXT_WITHOUT_URLS)

    private WebPageFactory factory = Mock()
    private WebPagePublisher publisher = Mock()
    @Subject private WebPageService webPageService

    def setup() {
        factory.create(WEB_PAGE_URL) >> webPage
    }

    def 'should return unique web pages based on url'() {
        given:
        webPageService = new WebPageService(factory, publisher)

        when:
        Set<WebPage> pages = webPageService.webPagesUnderDomain(WEB_PAGE_URL)

        then:
        pages == [webPage] as Set
    }

    def 'should publish webPage for further processing'() {
        given:
        webPageService = new WebPageService(factory, publisher)

        when:
        webPageService.webPagesUnderDomain(WEB_PAGE_URL)

        then:
        1 * publisher.publish(webPage)
    }
}
