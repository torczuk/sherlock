package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.factory.WebPageFactory;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebPageService {
    final private WebPageFactory factory;
    final private WebPagePublisher publisher;

    @Autowired
    public WebPageService(WebPageFactory factory, WebPagePublisher publisher) {
        this.factory = factory;
        this.publisher = publisher;
    }

    public Set<WebPage> webPagesUnderDomain(String homePage) throws IOException {
        Set<WebPage> result = new LinkedHashSet<>();
        Queue<String> locations = new LinkedList<>();
        locations.add(homePage);

        while (!locations.isEmpty()) {
            String page = locations.poll();
            WebPage webPage = factory.create(page);
            if (!result.contains(webPage)) {
                locations.addAll(selectDomainUrlsFromWebPage(homePage, webPage));
                publisher.publish(webPage);
            }
            result.add(webPage);
        }
        return result;
    }

    private static Set<String> selectDomainUrlsFromWebPage(String domain, WebPage webPage) {
        return webPage.urls().stream()
                .filter(url -> url.startsWith(domain))
                .collect(Collectors.toSet());
    }
}
