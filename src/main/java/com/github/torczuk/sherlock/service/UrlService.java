package com.github.torczuk.sherlock.service;

import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.torczuk.sherlock.util.spliterators.MatcherSpliterator.matcherStream;
import static java.util.stream.StreamSupport.stream;

public class UrlService {

    private static final String HREF_PATTERN = "href(\\s)*=(\\s)*\"([^\"]*)\"";
    Pattern pattern = Pattern.compile(HREF_PATTERN);

    public Set<String> urls(String content) {
        Stream<Matcher> stream = stream(matcherStream(pattern.matcher(content)), false);
        return stream.filter((matcher) -> matcher.groupCount() == 3)
                .map((matcher) -> matcher.group(3))
                .collect(Collectors.toSet());
    }
}
