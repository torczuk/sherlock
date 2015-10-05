package com.github.torczuk.sherlock.domain;

public class Content {
    private final String value;

    public Content(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
