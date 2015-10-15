package com.github.torczuk.sherlock.domain;

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
}
