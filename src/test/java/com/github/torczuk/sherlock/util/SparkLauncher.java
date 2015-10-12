package com.github.torczuk.sherlock.util;

import static spark.Spark.*;

public class SparkLauncher {
    public static void main(String[] args) {
        startLauncher();
    }

    public static void startLauncher() {
        Runnable spark = () -> {
            staticFileLocation("/public");
            get("/", (req, res) -> "<a href=\"/index.html\">index</a>");
        };
        new Thread(spark).start();
    }

    public static void stopLauncher() {
        stop();
    }
}
