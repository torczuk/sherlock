package com.github.torczuk.sherlock.domain.command.service

import spock.lang.Specification
import spock.lang.Subject

class WebPageServiceFeatureTest extends Specification {

    @Subject private  WebPageService webPageMapService = new WebPageService()
    def 'traverse the given location and find its all links'() {
        given:
        String homePage = 'http://127.0.0.1:4567'

        when:
        def map = webPageMapService.getAll(homePage) as Set

        then:
        def pages = map.collect{it.url} as Set
        pages.contains('http://127.0.0.1:4567') == true
        pages.contains('http://127.0.0.1:4567/') == true
        pages.contains('http://127.0.0.1:4567/index.html') == true
        pages.contains('http://127.0.0.1:4567/about.html') == true
        pages.contains('http://127.0.0.1:4567/offer.html') == true
        pages.contains('http://127.0.0.1:4567/about/history') == true
        pages.contains('http://127.0.0.1:4567/about/index.html') == true
    }
}
