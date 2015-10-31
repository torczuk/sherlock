package com.github.torczuk.sherlock.domain.command.factory;

import com.github.torczuk.sherlock.domain.command.model.Content;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.service.ContentService;

import java.io.IOException;

public class WebPageFactory {

    private ContentService contentService = new ContentService();

    public WebPage create(String url) throws IOException {
        Content content = contentService.from(url);
        return new WebPage(url, content);
    }

}
