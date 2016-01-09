package com.github.torczuk.sherlock.infrastructure.eventbus.publisher

import com.github.torczuk.sherlock.Sherlock
import com.github.torczuk.sherlock.domain.command.model.WebPage
import com.github.torczuk.sherlock.infrastructure.eventbus.consumer.LuceneIndexerBuilderConsumer
import com.github.torczuk.sherlock.infrastructure.eventbus.consumer.WebPageStorageConsumer
import com.github.torczuk.sherlock.testutils.Stubs
import com.github.torczuk.sherlock.util.matcher.HasTheSameWebPageAs
import org.junit.Test
import org.junit.runner.RunWith
import org.kubek2k.springockito.annotations.ReplaceWithMock
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.mockito.Matchers.argThat
import static org.mockito.Mockito.verify

@ContextConfiguration(
        classes = [Sherlock],
        loader = SpringockitoAnnotatedContextLoader
)
@RunWith(SpringJUnit4ClassRunner)
class EventBusWebPagePublisherIntegrationTest {

    @Autowired EventBusWebPagePublisher eventBusWebPagePublisher
    @Autowired @ReplaceWithMock LuceneIndexerBuilderConsumer indexerBuilderConsumer
    @Autowired @ReplaceWithMock WebPageStorageConsumer webPageStorageConsumer

    @Test
    void 'consume events send by webPagePublisher for all consumers' () {
        WebPage webPage = Stubs.WEB_PAGE_WITHOUT_URLS

        eventBusWebPagePublisher.publish(webPage)

        verify(indexerBuilderConsumer).accept(argThat(new HasTheSameWebPageAs(webPage)))
        verify(webPageStorageConsumer).accept(argThat(new HasTheSameWebPageAs(webPage)))
    }
}
