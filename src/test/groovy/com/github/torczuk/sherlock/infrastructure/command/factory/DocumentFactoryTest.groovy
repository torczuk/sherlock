package com.github.torczuk.sherlock.infrastructure.command.factory

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.domain.command.model.WebPage
import org.apache.lucene.document.Document
import spock.lang.Specification

import static com.github.torczuk.sherlock.domain.command.model.Content.content

class DocumentFactoryTest extends Specification {
    private WebPage webPage = new WebPage('example.com', Optional.of(content('some content')))

    def 'create Document having conent and url fields from WebPage' (){
        given:
        DocumentFactory documentFactory = new DocumentFactory()

        when:
        Document create = documentFactory.create(webPage)

        then:
        'some content' == create.get('content')
        'example.com' == create.get('url')
    }

    def 'create Document having url stored and content not stored in index' (){
        given:
        DocumentFactory documentFactory = new DocumentFactory()

        when:
        Document create = documentFactory.create(webPage)

        then:
        !create.getField('content').fieldType().stored()
        create.getField('url').fieldType().stored()
    }
}
