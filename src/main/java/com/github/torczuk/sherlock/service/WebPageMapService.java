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
    private FindLinks findLinks = new FindLinks();

    public Map<String, Content> map(HomePage homePage) throws IOException {
        Map<String, Content> result = new HashMap<>();
        Queue<WebPage> locations = new LinkedList<>();
        locations.add(homePage);

        while (!locations.isEmpty()) {
            WebPage page = locations.poll();
            Content pageContent = contentService.contentFor(page);
            Set<SubPage> pages = findLinks.apply(pageContent.toString())
                    .stream()
                    .map(link -> new SubPage(homePage, link))
                    .collect(toSet());
            for (SubPage link : pages) {
                if (!result.containsKey(link.url())) {
                    locations.add(link);
                }
            }
            result.put(page.url(), pageContent);
        }

        return result;
    }
}
