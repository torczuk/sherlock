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
        if ("/".equals(url)) {
            return homePageUrl;
        } else if (url.startsWith("/")) {
            return homePageUrl + url;
        } else {
            return homePageUrl + "/" + url;
        }
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
