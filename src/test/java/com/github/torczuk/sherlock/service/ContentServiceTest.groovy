package com.github.torczuk.sherlock.service

import com.github.torczuk.sherlock.domain.Content
import com.github.torczuk.sherlock.domain.EntryPoint
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class ContentServiceTest extends Specification {

    @Subject
    ContentService contentService = new ContentService()
    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder();

    def 'find content for given entry point'() {
        given:
        EntryPoint entryPoint = anEntryPoint()

        when:
        Content contentFor = contentService.contentFor(entryPoint)

        then:
        contentFor.toString() == 'Content of the file'
    }

    EntryPoint anEntryPoint() {
        File entryPointFile = temporaryFolder.newFile("index")
        entryPointFile.withWriter { writer ->
            writer.write('Content of the file')
        }
        new EntryPoint("file:///${entryPointFile.absolutePath}")
    }
}
