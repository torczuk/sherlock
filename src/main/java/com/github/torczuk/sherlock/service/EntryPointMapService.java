package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.HomePage;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class EntryPointMapService {

    private ContentService contentService = new ContentService();
    private FindLinks findLinks = new FindLinks();

    public Map<String, Content> map(HomePage homePage) throws IOException {
        Map<String, Content> map = new HashMap<>();

        Content content = contentService.contentFor(homePage);
        map.put(homePage.url(), content);

        Queue<String> locations = new LinkedList<>();
        locations.add(homePage.url());

        while (!locations.isEmpty()) {
            String location = locations.poll();
            HomePage entry = new HomePage(location);
            Content entryContent = contentService.contentFor(entry);
            Set<String> links = findLinks.apply(entryContent.toString()).stream().map(link ->
                    {
                        if (link.startsWith("http")) return link;
                        else return homePage.url() + link;
                    }
            ).collect(toSet());
            for (String link : links) {
                if (!map.containsKey(link)) {
                    locations.add(link);
                }
            }
            if (!map.containsKey(location)) {
                map.put(location, entryContent);
            }
        }

        return map;
    }
}
