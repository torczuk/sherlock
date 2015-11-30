package com.github.torczuk.sherlock.testutils

import com.github.torczuk.sherlock.domain.command.model.Content
import com.github.torczuk.sherlock.domain.command.model.WebPage


class Stubs {
    static Content TEXT_WITHOUT_URLS = new Content('Empty content')
    static WebPage WEB_PAGE_WITHOUT_URLS = new WebPage('http://example.com', TEXT_WITHOUT_URLS)
}

