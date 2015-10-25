package com.github.torczuk.sherlock.domain

import spock.lang.Specification


class WebPageTest extends Specification {

    def content = '''This is a example of any web page
there are some links e.g. <a href="http://example.com">home</a>
some links to the subpages e.g. <a href="/offer">offer</a>
and some other links to the other pages <a href="http://google.pl">google</a>
'''

    def 'should extract all links form content of the page'(){
        given:
        WebPage webPage = new WebPage('http://example.com', new Content(content))

        when:
        def urls = webPage.urls()

        then:
        ['http://example.com',
         'http://example.com/offer',
         'http://google.pl'] as Set == urls
    }
}
