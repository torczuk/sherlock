package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.model.Content
import spock.lang.Specification
import spock.lang.Subject

class ContentParserIntegrationTest extends Specification {

    private static final String HTML = '''<html>
                <head></head>
                <body>
                    <div id="any">Some text goes here...</div>
                </body>
            <html>'''

    @Subject ContentParser parser

    def 'should extract plain text from html page'() {
        given:
        Content content = new Content(HTML)
        parser = new ContentParser()

        when:
        String text = parser.parse(content)

        then:
        'Some text goes here' == text
    }
}
