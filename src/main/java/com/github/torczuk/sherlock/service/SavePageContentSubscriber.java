package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.WebPage;
import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SavePageContentSubscriber {
    private static final Set<Integer> CHARS_TO_ESCAPE = new HashSet<>();
    private static final Integer ESCAPED_CHAR = Integer.valueOf('_');

    static {
        CHARS_TO_ESCAPE.add(Integer.valueOf(':'));
        CHARS_TO_ESCAPE.add(Integer.valueOf('/'));
    }
    private String dir;

    public SavePageContentSubscriber(String dir) {
        this.dir = dir;
    }

    @Subscribe
    public void save(WebPage webPage) throws IOException {
        Content content = webPage.content();
        String transformedUrl = webPage.url().codePoints()
                .map(this::escapeUrl)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        File file = new File(dir, transformedUrl);
        try(FileWriter fw = new FileWriter(file);) {
            fw.write(content.toString());
        }
    }

    private int escapeUrl(int sign) {
        return CHARS_TO_ESCAPE.contains(sign) ? ESCAPED_CHAR : sign;
    }
}
