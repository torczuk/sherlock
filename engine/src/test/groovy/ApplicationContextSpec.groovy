import com.github.torczuk.sherlock.Sherlock
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import spock.lang.Specification

class ApplicationContextSpec extends Specification {

    def 'should build application context'() {
        given:
        ApplicationContext context = new AnnotationConfigApplicationContext(Sherlock.class);

        expect:
        context != null
    }
}
