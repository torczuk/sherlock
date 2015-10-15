package com.github.torczuk.sherlock.domain

import spock.lang.Specification

class SubPageTest extends Specification {

    def 'create valid url when subpage has no slash at the beginning'() {
        given:
        HomePage homePage = new HomePage("http://example.com")

        when:
        SubPage subPage = new SubPage(homePage, "subPage")

        then:
        subPage.url() == 'http://example.com/subPage'
    }

    def 'create valid url when subpage starts from slash'() {
        given:
        HomePage homePage = new HomePage("http://example.com")

        when:
        SubPage subPage = new SubPage(homePage, "/subPage")

        then:
        subPage.url() == 'http://example.com/subPage'
    }
}
