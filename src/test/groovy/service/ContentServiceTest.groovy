package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.model.Content
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class ContentServiceTest extends Specification {

    @Subject private ContentService contentService = new ContentService()
    @Rule private TemporaryFolder temporaryFolder = new TemporaryFolder();

    def 'find content for given entry point'() {
        given:
        String uri = locationToContent()

        when:
        Content contentFor = contentService.from(uri)

        then:
        contentFor.toString() == 'Content of the file'
    }

    String locationToContent() {
        File entryPointFile = temporaryFolder.newFile("index")
        entryPointFile.withWriter { writer ->
            writer.write('Content of the file')
        }
        return "file:///${entryPointFile.absolutePath}"
    }
}
