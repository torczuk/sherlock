package com.github.torczuk.sherlock.domain.command.factory;

import com.github.torczuk.sherlock.domain.command.model.Content;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WebPageFactory {

    private ContentService contentService;

    @Autowired
    public WebPageFactory(ContentService contentService) {
        this.contentService = contentService;
    }

    public WebPage create(String url) throws IOException {
        Optional<Content> content = contentService.from(url);
        return new WebPage(url, content);
    }

}
