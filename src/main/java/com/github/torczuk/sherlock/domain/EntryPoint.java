package com.github.torczuk.sherlock.domain;

public class EntryPoint {

   private final String location;

    public EntryPoint(String location) {
        this.location = location;
    }

    public String location() {
        return location;
    }
}
