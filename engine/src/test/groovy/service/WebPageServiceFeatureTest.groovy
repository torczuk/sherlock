package com.github.torczuk.sherlock.domain.command.service

import com.github.torczuk.sherlock.Sherlock
import com.github.torczuk.sherlock.domain.command.model.WebPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = [ Sherlock.class ])
class WebPageServiceFeatureTest extends Specification {

    @Autowired private WebPageService webPageMapService

    def 'traverse the given location and find its all links'() {
        given:
        String homePage = 'http://127.0.0.1:4567'

        when:
        Set<WebPage> map = webPageMapService.webPagesUnderDomain(homePage)

        then:
        def urls = map.collect { page -> page.url } as Set
        'http://127.0.0.1:4567' in urls
        'http://127.0.0.1:4567' in urls
        'http://127.0.0.1:4567/'in urls
        'http://127.0.0.1:4567/index.html'in urls
        'http://127.0.0.1:4567/about.html'in urls
        'http://127.0.0.1:4567/offer.html'in urls
        'http://127.0.0.1:4567/about/history'in urls
        'http://127.0.0.1:4567/about/index.html'in urls
    }
}
