package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.EntryPoint;

import java.util.HashMap;
import java.util.Map;

public class EntryPointMapService {

    private ContentService contentService;
    private FindLinks findLinks;

    public Map<String, Content> map(EntryPoint entryPoint) {
        return new HashMap<>();
    }
}
