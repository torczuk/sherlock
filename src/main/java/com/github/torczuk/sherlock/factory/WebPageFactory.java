package com.github.torczuk.sherlock.factory;

import com.github.torczuk.sherlock.domain.Content;
import com.github.torczuk.sherlock.domain.WebPage;
import com.github.torczuk.sherlock.service.ContentService;

import java.io.IOException;

public class WebPageFactory {

    private ContentService contentService = new ContentService();

    public WebPage create(String url) throws IOException {
        Content content = contentService.from(url);
        return new WebPage(url, content);
    }

}
