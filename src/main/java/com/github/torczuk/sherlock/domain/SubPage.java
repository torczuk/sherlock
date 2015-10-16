package com.github.torczuk.sherlock.domain;

import java.net.URI;
import java.util.Objects;

public class SubPage implements WebPage {

    final private WebPage parent;
    final private String url;

    public SubPage(HomePage parent, String url) {
        this.url = URI.create(parent.url()).resolve(url).toString();
        this.parent = parent;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public WebPage parent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebPage)) return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(url, webPage.url());
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
