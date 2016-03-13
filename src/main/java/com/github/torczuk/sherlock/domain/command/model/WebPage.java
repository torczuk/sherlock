package com.github.torczuk.sherlock.domain.command.model;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.torczuk.sherlock.util.spliterators.MatcherSpliterator.matcherStream;
import static java.util.stream.StreamSupport.stream;

public class WebPage {
    private static final String HREF_PATTERN = "href(\\s)*=(\\s)*\"([^\"]*)\"";
    private static final Pattern pattern = Pattern.compile(HREF_PATTERN);

    final private Optional<Content> content;

    final private String url;

    public WebPage(String url, Optional<Content> content) {
        validate(url);
        this.url = url;
        this.content = content;
    }

    public WebPage(String url, Content content) {
        validate(url);
        this.url = url;
        this.content = Optional.of(content);
    }

    public String url() {
        return url;
    }

    public Optional<Content> content() {
        return content;
    }

    public Set<String> urls() {
        return content.map(value -> extractUrlsFrom(value.toString())).orElseGet(HashSet::new);
    }

    private Set<String> extractUrlsFrom(String text) {
        Stream<Matcher> stream = stream(matcherStream(pattern.matcher(text)), false);
        return stream.filter((matcher) -> matcher.groupCount() == 3)
                .map((matcher) -> matcher.group(3))
                .map(href -> URI.create(url).resolve(href).toString())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebPage webPage = (WebPage) o;
        return Objects.equals(url, webPage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return url;
    }

    public String host() {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String path() {
        try {
            return new URL(url).getPath();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static void validate(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
