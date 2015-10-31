package com.github.torczuk.sherlock.domain.command.repository;

import com.github.torczuk.sherlock.domain.command.model.WebPage;

public interface WebPageRepository {

    void save(WebPage webPage);

}
