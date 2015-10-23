package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.HomePage;
import com.github.torczuk.sherlock.domain.SubPage;
import com.github.torczuk.sherlock.domain.WebPage;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class WebPageMapService {
    private ContentService contentService = new ContentService();
    private UrlService urlService = new UrlService();

    public Map<String, Content> map(HomePage homePage) throws IOException {
        Map<String, Content> result = new HashMap<>();
        Queue<WebPage> locations = new LinkedList<>();
        locations.add(homePage);

        while (!locations.isEmpty()) {
            WebPage page = locations.poll();
            Content pageContent = contentService.contentFor(page);
            Set<SubPage> pages = urlService.urls(pageContent.toString())
                    .stream()
                    .map(link -> new SubPage(page, link))
                    .filter(subPage -> !result.containsKey(subPage.url()))
                    .collect(toSet());
            locations.addAll(pages);
            result.put(page.url(), pageContent);
        }

        return result;
    }
}
