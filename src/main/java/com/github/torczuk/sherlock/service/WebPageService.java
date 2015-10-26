package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.WebPage;
import com.github.torczuk.sherlock.factory.WebPageFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class WebPageService {
    WebPageFactory factory = new WebPageFactory();

    public Set<WebPage> getAll(String homePage) throws IOException {
        Set<WebPage> result = new LinkedHashSet<>();
        Queue<String> locations = new LinkedList<>();
        locations.add(homePage);

        while (!locations.isEmpty()) {
            String page = locations.poll();
            WebPage webPage = factory.create(page);
            if(!result.contains(webPage)) {
                locations.addAll(urlsInDomain(homePage, webPage));
            }
            result.add(webPage);
        }
        return result;
    }

    private static Set<String> urlsInDomain(String homePage, WebPage webPage) {
        return webPage.urls().stream()
                .filter(url -> url.startsWith(homePage))
                .collect(Collectors.toSet());
    }
}