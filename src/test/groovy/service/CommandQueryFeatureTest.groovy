package service

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.domain.command.model.WebPage
import com.github.torczuk.sherlock.domain.command.repository.WebPageWriteRepository
import com.github.torczuk.sherlock.domain.query.model.Result
import com.github.torczuk.sherlock.domain.query.repository.ResultReadRepository
import com.github.torczuk.sherlock.infrastructure.command.repository.LuceneWebPageWriteRepository
import com.github.torczuk.sherlock.infrastructure.query.repository.LuceneResultReadRepository
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Subject

class CommandQueryFeatureTest extends Specification {

    @Rule
    private TemporaryFolder indexFolder = new TemporaryFolder()
    @Subject
    private WebPageWriteRepository writeRepository
    @Subject
    private ResultReadRepository readRepository

    def setup() {
        String pathToIndex = indexFolder.root.absolutePath
        writeRepository = new LuceneWebPageWriteRepository(pathToIndex)
        readRepository = new LuceneResultReadRepository(pathToIndex)
    }

    def 'should update model first and next execute search'() {
        given:
        WebPage webPage = new WebPage('http://example.com', new Content('Najlepsze na świecie pierogi z jajek, masła orzechowego i mąki robi moja mama'))

        when:
        writeRepository.write(webPage)
        List<Result> results = readRepository.find(['masło orzechowe'] as Set)
        List<Result> results2 = readRepository.find(['orzechy'] as Set)

        then:
        1 == results.size()
        0 == results2.size()
    }
}