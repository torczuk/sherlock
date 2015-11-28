package com.github.torczuk.sherlock.domain.command.service

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class ContentServiceTest extends Specification {

    @Subject private ContentService contentService = new ContentService()
    @Rule private TemporaryFolder temporaryFolder = new TemporaryFolder();

    def 'should return content when requested location exists'() {
        given:
        String uri = locationToContent()

        when:
        Optional content = contentService.from(uri)

        then:
        content.isPresent()
        content.get().toString() == 'Content of the file'
    }

    def 'should return empty content when requested location is not found'() {
        given:
        String uri = 'non-existing-location'

        when:
        Optional content = contentService.from(uri)

        then:
        !content.isPresent()
    }

    String locationToContent() {
        File entryPointFile = temporaryFolder.newFile("index")
        entryPointFile.withWriter { writer ->
            writer.write('Content of the file')
        }
        return "file:///${entryPointFile.absolutePath}"
    }
}
