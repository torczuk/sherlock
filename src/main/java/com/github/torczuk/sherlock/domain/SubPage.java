package com.github.torczuk.sherlock.domain;

import java.util.Objects;

public class SubPage implements WebPage {

    final private HomePage homePage;
    final private String url;

    public SubPage(HomePage homePage, String url) {
        this.url = concat(homePage.url(), url);
        this.homePage = homePage;
    }

    public HomePage webPage() {
        return homePage;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public HomePage homePage() {
        return homePage;
    }

    private static String concat(String homePageUrl, String url) {
        return url.startsWith("/") ? homePageUrl + url : homePageUrl + "/" + url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubPage subPage = (SubPage) o;
        return Objects.equals(url, subPage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
