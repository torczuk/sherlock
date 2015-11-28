package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.Content;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Collector;

@Service
public class ContentService {
    public Optional<Content> from(String url) {
        try {
            URL uri = new URL(url);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(uri.openStream()))) {
                String value = br.lines().collect(Collector.of(StringBuilder::new,
                        (builder, line) -> builder.append(line),
                        StringBuilder::append,
                        StringBuilder::toString));
                return Optional.of(new Content(value));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
