package com.github.torczuk.sherlock.domain;

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
}
