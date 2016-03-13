package com.github.torczuk.sherlock.domain.command.repository;

import com.github.torczuk.sherlock.domain.command.model.WebPage;

import java.util.Collection;

public interface WebPageWriter {

    void write(WebPage webPage);

    void write(Collection<WebPage> webPages);

    void flush();
}
