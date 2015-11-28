package com.github.torczuk.sherlock.domain

import com.github.torczuk.sherlock.domain.command.model.WebPage
import spock.lang.Specification

import static com.github.torczuk.sherlock.domain.command.model.Content.content


class WebPageTest extends Specification {

    def text = '''This is a example of any web page
there are some links e.g. <a href="http://example.com">home</a>
some links to the subpages e.g. <a href="/offer">offer</a>
and some other links to the other pages <a href="http://google.pl">google</a>
'''

    def 'should extract all links form content of the page'() {
        given:
        WebPage webPage = new WebPage('http://example.com', Optional.of(content(text)))

        expect:
        webPage.urls() == ['http://example.com',
                           'http://example.com/offer',
                           'http://google.pl'] as Set
    }

    def 'should return valid file name representation of url'() {
        given:
        WebPage webPage = new WebPage('http://example.com', Optional.of(content(text)))

        expect:
        webPage.fileNameUrl() == 'http___example.com'
    }

    def 'should return empty urls for missing content'() {
        given:
        WebPage webPage = new WebPage('http://example.com', Optional.empty())

        expect:
        webPage.urls().empty
    }
}
