package service

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.domain.command.model.WebPage
import com.github.torczuk.sherlock.domain.command.repository.WebPageWriteRepository
import com.github.torczuk.sherlock.domain.query.model.Result
import com.github.torczuk.sherlock.domain.query.repository.ResultReadRepository
import com.github.torczuk.sherlock.infrastructure.command.factory.DocumentFactory
import com.github.torczuk.sherlock.infrastructure.command.repository.LuceneWebPageWriteRepository
import com.github.torczuk.sherlock.infrastructure.query.repository.LuceneResultReadRepository
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class CommandQueryFeatureTest extends Specification {

    @Rule private TemporaryFolder indexFolder = new TemporaryFolder()
    @Subject private WebPageWriteRepository writeRepository
    @Subject private ResultReadRepository readRepository

    def setup() {
        String pathToIndex = indexFolder.root.absolutePath
        writeRepository = new LuceneWebPageWriteRepository(pathToIndex, new DocumentFactory())
        readRepository = new LuceneResultReadRepository(pathToIndex)
    }

    def 'should update model first and next execute search'() {
        given:
        WebPage webPage = new WebPage('http://example.com', new Content('Najlepsze na świecie pierogi z jajek, masła orzechowego i mąki robi moja mama'))

        when:
        writeRepository.write(webPage)
        writeRepository.flush();
        List<Result> results = readRepository.find(['masło orzechowe'] as Set)
        List<Result> results2 = readRepository.find(['orzechy'] as Set)

        then:
        1 == results.size()
        0 == results2.size()
    }

    def 'multiple search on pages should return any url containing keyword' () {
        given:
        WebPage firstPage = new WebPage('http://example.com/1', new Content('Cukier, Jajka, masło, cebula'))
        WebPage secondPage = new WebPage('http://example.com/2', new Content('parrot, ryba'))

        when:
        writeRepository.write([firstPage, secondPage])
        writeRepository.flush();
        List<Result> result = readRepository.find(['masło', 'ryba'] as Set)

        then:
        ['http://example.com/1', 'http://example.com/2'] as Set == result.collect {it.url()} as Set
    }

    def 'multiple search on pages should return the most accurate url as first result' () {
        given:
        WebPage firstPage = new WebPage('http://example.com/1', new Content('Cukier, Jajka, masło, cebula'))
        WebPage secondPage = new WebPage('http://example.com/2', new Content('cebula, ryba, jajka'))
        WebPage third = new WebPage('http://example.com/3', new Content('jajka'))

        when:
        writeRepository.write([third, secondPage, firstPage])
        writeRepository.flush();
        List<Result> result = readRepository.find(['cukier', 'Jajka', 'Cebula'] as Set)

        then:
        3 == result.size()
        'http://example.com/1' == result[0].url()
        'http://example.com/2' == result[1].url()
        'http://example.com/3' == result[2].url()
    }
}
