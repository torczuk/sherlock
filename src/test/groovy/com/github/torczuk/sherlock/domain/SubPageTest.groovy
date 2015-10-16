package com.github.torczuk.sherlock.domain

import spock.lang.Specification
import spock.lang.Unroll

class SubPageTest extends Specification {

    @Unroll
    def 'create valid url when when from #page #sub '() {
        given:
        WebPage main = new HomePage(page)

        when:
        SubPage subPage = new SubPage(main, sub)

        then:
        expected == subPage.url()

        where:
        page                            | sub   | expected
        'http://example.com/1'          | '1'   | 'http://example.com/1'
        'http://example.com/'           | '/'   | 'http://example.com/'
        'http://example.com/1.html'     | '2'   | 'http://example.com/2'
        'http://example.com/1/2'        | '3'   | 'http://example.com/1/3'
    }
}
