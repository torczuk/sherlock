package com.github.torczuk.sherlock.service;

import com.github.torczuk.sherlock.domain.model.Content;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ContentParser {

    public String parse(Content content) {
        try(InputStream stream = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));) {
            BodyContentHandler handler = new BodyContentHandler();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(stream, handler, new Metadata());
            return handler.toString().trim();
        } catch (TikaException | SAXException | IOException ex) {
            throw new RuntimeException("Cannot extract content from page");
        }
    }

}
