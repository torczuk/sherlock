package com.github.torczuk.sherlock.domain;

import java.util.Objects;

public class HomePage implements WebPage {

    private final String url;

    public HomePage(String url) {
        this.url = url;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public HomePage homePage() {
        return this;
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
