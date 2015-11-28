package service

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

import static com.github.torczuk.sherlock.domain.command.model.Content.content

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
        WebPage webPage = new WebPage('http://example.com', content('Najlepsze na świecie pierogi z jajek, masła orzechowego i mąki robi moja mama'))

        when:
        writeRepository.write(webPage)
        writeRepository.flush();
        List<Result> results = readRepository.find(['masło orzechowe'] as Set)
        List<Result> results2 = readRepository.find(['orzechy'] as Set)

        then:
        results.size() == 1
        results2.size() == 0
    }

    def 'multiple search on pages should return any url containing keyword'() {
        given:
        WebPage firstPage = new WebPage('http://example.com/1', content('Cukier, Jajka, masło, cebula'))
        WebPage secondPage = new WebPage('http://example.com/2', content('parrot, ryba'))

        when:
        writeRepository.write([firstPage, secondPage])
        writeRepository.flush();
        List<Result> result = readRepository.find(['masło', 'ryba'] as Set)

        then:
        result.collect { it.url() } as Set == ['http://example.com/1', 'http://example.com/2'] as Set
    }

    def 'multiple search on pages should return the most accurate url as first result'() {
        given:
        WebPage firstPage = new WebPage('http://example.com/1', content('Cukier, Jajka, masło, cebula'))
        WebPage secondPage = new WebPage('http://example.com/2', content('cebula, ryba, jajka'))
        WebPage third = new WebPage('http://example.com/3', content('jajka'))

        when:
        writeRepository.write([third, secondPage, firstPage])
        writeRepository.flush();
        List<Result> result = readRepository.find(['cukier', 'Jajka', 'Cebula'] as Set)

        then:
        3 == result.size()
        result[0].url() == 'http://example.com/1'
        result[1].url() == 'http://example.com/2'
        result[2].url() == 'http://example.com/3'
    }
}
