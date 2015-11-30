package com.github.torczuk.sherlock;

import static spark.Spark.*;

public class SparkLauncher {
    public static void main(String[] args) {
        startLauncher();
    }

    public static void startLauncher() {
        staticFileLocation("/public");
        get("/", (req, res) -> "<a href=\"/index.html\">index</a>");
    }

    public static void stopLauncher() {
        stop();
    }
}
