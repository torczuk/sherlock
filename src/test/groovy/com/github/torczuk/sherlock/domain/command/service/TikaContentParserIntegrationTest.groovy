package com.github.torczuk.sherlock.domain.command.service

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.infrastructure.service.TikaContentParser
import spock.lang.Specification
import spock.lang.Subject

class TikaContentParserIntegrationTest extends Specification {

    private static final String HTML = '''<html>
                <head></head>
                <body>
                    <div id="any">Some text goes here...</div>
                </body>
            <html>'''

    @Subject TikaContentParser parser

    def 'should extract plain text from html page'() {
        given:
        Content content = new Content(HTML)
        parser = new TikaContentParser()

        when:
        String text = parser.parse(content)

        then:
        'Some text goes here' == text
    }
}
