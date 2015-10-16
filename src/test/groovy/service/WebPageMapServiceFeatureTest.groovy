package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.HomePage
import spock.lang.Specification

class WebPageMapServiceFeatureTest extends Specification {

    def 'traverse the given location and find its all links'() {
        given:
        HomePage entryPoint = new HomePage('http://127.0.0.1:4567')
        WebPageMapService entryPointMapService = new WebPageMapService()

        when:
        def map = entryPointMapService.map(entryPoint) as Map

        then:
        map.collect { key, value -> key } as Set ==
                ['http://127.0.0.1:4567',
                 'http://127.0.0.1:4567/',
                 'http://127.0.0.1:4567/index.html',
                 'http://127.0.0.1:4567/about.html',
                 'http://127.0.0.1:4567/offer.html',
                 'http://127.0.0.1:4567/about/history',
                 'http://127.0.0.1:4567/about/index.html'] as Set
    }
}
