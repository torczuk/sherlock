package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Collector;

@Service
public class ContentService {
    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

    public Optional<Content> from(String url) {
        try {
            logger.info("Getting content for url {}", url);
            URL uri = new URL(url);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(uri.openStream()))) {
                String value = br.lines().collect(Collector.of(StringBuilder::new,
                        (builder, line) -> builder.append(line),
                        StringBuilder::append,
                        StringBuilder::toString));
                return Optional.of(new Content(value));
            }
        } catch (IOException ex) {
            logger.error("Can not read content", ex);
            return Optional.empty();
        }
    }
}
