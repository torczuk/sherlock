package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.Content;

public interface ContentParser {

    String parse(Content content);
}
