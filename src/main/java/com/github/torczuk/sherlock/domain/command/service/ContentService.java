package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.Content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collector;

public class ContentService {
    public Content from(String url) throws IOException {
        URL uri = new URL(url);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(uri.openStream()))) {
            String value = br.lines().collect(Collector.of(StringBuilder::new,
                    (builder, line) -> builder.append(line),
                    StringBuilder::append,
                    StringBuilder::toString));
            return new Content(value);
        }
    }
}
