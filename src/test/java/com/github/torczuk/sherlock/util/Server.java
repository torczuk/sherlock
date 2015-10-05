package com.github.torczuk.sherlock.util;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class Server {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        staticFileLocation("/public");
        get("/", (req, res) -> "/index.html");
    }

    public static void stop() {
        stop();
    }
}
