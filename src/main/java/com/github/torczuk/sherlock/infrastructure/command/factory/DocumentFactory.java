package com.github.torczuk.sherlock.infrastructure.command.factory;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.springframework.stereotype.Service;

@Service
public class DocumentFactory {

    public Document create(WebPage webPage) {
        Document doc = new Document();
        doc.add(new StringField("url", webPage.url(), Field.Store.YES));
        doc.add(new TextField("content", webPage.content().toString(), Field.Store.NO));
        return doc;
    }
}
