package com.github.torczuk.sherlock.infrastructure.eventbus.consumer;

import com.github.torczuk.sherlock.domain.command.model.Content;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class SaveToFilePageContentConsumer implements Consumer<Event<WebPage>> {
    private static final Logger logger = LoggerFactory.getLogger(SaveToFilePageContentConsumer.class);

    private String dir;

    @Autowired
    public SaveToFilePageContentConsumer(@Value("${content.dir.path}") String dir) {
        this.dir = dir;
        new File(dir).mkdir();
    }

    public void accept(Event<WebPage> event) {
        logger.info("Saving content to file or url - {}", event.getData().url());
        WebPage webPage = event.getData();
        if (webPage.content().isPresent()) {
            Content content = webPage.content().get();
            File file = resolveFile(webPage);
            write(content, file);
        }
    }

    private void write(Content content, File file) {
        try (FileWriter fw = new FileWriter(file)) {
            file.createNewFile();
            fw.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    private File resolveFile(WebPage webPage) {
        String webPageHost = webPage.host();
        String webPagePath = webPage.path();

        if ("".equals(webPagePath)) {
            return new File(dir, webPageHost);
        } else {
            String pathPrefix = webPagePathPrefix(webPagePath);
            String pathSuffix = webPagePathSuffix(webPagePath);
            File rootFile = Paths.get(dir, webPageHost, pathPrefix).toFile();
            rootFile.mkdirs();
            return new File(rootFile, pathSuffix);
        }
    }

    private static String webPagePathSuffix(String webPagePath) {
        return webPagePath.substring(webPagePath.lastIndexOf("/"));
    }

    private static String webPagePathPrefix(String webPagePath) {
        return webPagePath.substring(0, webPagePath.lastIndexOf("/"));
    }
}
