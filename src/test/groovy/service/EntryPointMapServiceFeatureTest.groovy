package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.EntryPoint
import spock.lang.Specification
import spock.lang.*

class EntryPointMapServiceFeatureTest extends Specification {

    def 'traverse the given location and find its all links'() {
        given:
        EntryPoint entryPoint = new EntryPoint('http://127.0.0.1:4567')
        EntryPointMapService entryPointMapService = new EntryPointMapService()

        when:
        def map = entryPointMapService.map(entryPoint) as Map

        then:
        map.collect { key, value -> key } as Set ==
                ['http://127.0.0.1:4567',
                 'http://127.0.0.1:4567/index.html',
                 'http://127.0.0.1:4567/about.html',
                 'http://127.0.0.1:4567/offer.html'] as Set
    }
}
