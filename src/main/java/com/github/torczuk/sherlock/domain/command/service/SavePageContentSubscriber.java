package com.github.torczuk.sherlock.domain.command.service;

import com.github.torczuk.sherlock.domain.command.model.Content;
import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SavePageContentSubscriber implements Consumer<Event<WebPage>> {

    private String dir;

    @Autowired
    public SavePageContentSubscriber(@Value("${content.dir.path}")String dir) {
        this.dir = dir;
    }

    public void accept(Event<WebPage> event) {
        WebPage webPage = event.getData();
        if (webPage.content().isPresent()) {
            Content content = webPage.content().get();
            String transformedUrl = webPage.fileNameUrl();

            File file = new File(dir, transformedUrl);
            try (FileWriter fw = new FileWriter(file);) {
                fw.write(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
                throw new AppException(e);
            }
        }
    }

}
