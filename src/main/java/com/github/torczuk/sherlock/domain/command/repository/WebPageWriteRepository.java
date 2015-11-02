package com.github.torczuk.sherlock.domain.command.repository;

import com.github.torczuk.sherlock.domain.command.model.WebPage;

public interface WebPageWriteRepository {

    void write(WebPage webPage);

    void flush();
}
