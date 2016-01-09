package com.github.torczuk.sherlock.infrastructure.eventbus

import com.github.torczuk.sherlock.Sherlock
import com.github.torczuk.sherlock.domain.command.model.WebPage
import com.github.torczuk.sherlock.util.matcher.HasTheSameWebPageAs
import org.junit.Test
import org.junit.runner.RunWith
import org.kubek2k.springockito.annotations.ReplaceWithMock
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static com.github.torczuk.sherlock.testutils.Stubs.WEB_PAGE_WITHOUT_URLS
import static org.mockito.Matchers.argThat
import static org.mockito.Mockito.verify;


@ContextConfiguration(
        classes = [Sherlock],
        loader = SpringockitoAnnotatedContextLoader
)
@RunWith(SpringJUnit4ClassRunner)
class EventBusWebPagePublisherIntegrationTest {

    @Autowired EventBusWebPagePublisher eventBusWebPagePublisher
    @Autowired @ReplaceWithMock LuceneIndexerBuilderConsumer indexerBuilderConsumer

    @Test
    void 'consume events send by webPagePublisher' () {
        WebPage webPage = WEB_PAGE_WITHOUT_URLS

        eventBusWebPagePublisher.publish(webPage)

        verify(indexerBuilderConsumer).accept(argThat(new HasTheSameWebPageAs(webPage)))
    }
}
