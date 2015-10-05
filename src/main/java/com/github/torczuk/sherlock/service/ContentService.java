package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.EntryPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collector;

public class ContentService {
    public Content contentFor(EntryPoint entryPoint) throws IOException {
        String location = entryPoint.location();
        URL uri = new URL(location);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(uri.openStream()))) {
            String value = br.lines().collect(Collector.of(StringBuilder::new,
                    (builder, line) -> builder.append(line),
                    StringBuilder::append,
                    StringBuilder::toString));
            return new Content(value);
        }
    }
}
