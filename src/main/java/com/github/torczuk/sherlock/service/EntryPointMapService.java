package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.EntryPoint;

import java.io.IOException;
import java.util.*;

public class EntryPointMapService {

    private ContentService contentService = new ContentService();
    private FindLinks findLinks = new FindLinks();

    public Map<String, Content> map(EntryPoint entryPoint) throws IOException {
        Map<String, Content> map = new HashMap<>();

        Content content = contentService.contentFor(entryPoint);
        map.put(entryPoint.location(), content);

        Queue<String> locations = new LinkedList<>();
        locations.add(entryPoint.location());

        while(!locations.isEmpty()) {
            String location = locations.poll();
            EntryPoint entry = new EntryPoint(location);
            Content entryContent = contentService.contentFor(entry);
            Set<String> links = findLinks.apply(entryContent.toString());
            for (String link : links) {
                if(!map.containsKey(link)) {
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
