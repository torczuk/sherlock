package com.github.torczuk.sherlock.domain.command.model;

public class Content {
    private final String value;

    public static Content content(String value) {
        return new Content(value);
    }

    public Content(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
