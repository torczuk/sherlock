package com.github.torczuk.sherlock.service

import spock.lang.Specification
import spock.lang.Subject


class FindLinksTest extends Specification {

    @Subject
    private FindLinks findLinks = new FindLinks();

    def 'find all occurrences of entry point on the content'() {
        given:
        String content = '''
        <html/>
            <body>
                Welcome!
                <a href = "/location1">some text</a>
                <a href= "/location2">
                    <img typeof="foaf:Image" src="some location" alt="">
                </a>
                Good bye!
            </body>
        </html>
        '''

        when:
        Set links = findLinks.apply(content)

        then:
        links == ["/location1", "/location2"] as Set
    }
}
