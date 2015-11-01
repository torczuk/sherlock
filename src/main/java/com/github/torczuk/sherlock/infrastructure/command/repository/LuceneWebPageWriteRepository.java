package com.github.torczuk.sherlock.infrastructure.command.repository;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.repository.WebPageWriteRepository;
import com.github.torczuk.sherlock.domain.exception.AppException;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;

public class LuceneWebPageWriteRepository implements WebPageWriteRepository {

    private String indexPath;
    private IndexWriter indexWriter;

    public LuceneWebPageWriteRepository(String indexPath) {
        this.indexPath = indexPath;
    }

    @Override
    public void write(WebPage webPage) {
        try {
            IndexWriter index = getIndex();
            Document doc = new Document();
            doc.add(new StringField("url", webPage.url(), Field.Store.YES));
            doc.add(new TextField("content", webPage.content().toString(), Field.Store.NO));
            index.addDocument(doc);
            index.close();
        } catch (IOException e) {
            throw new AppException("can not add field to the index", e);
        }
    }

    private IndexWriter getIndex() {
        try {
            if (indexWriter == null) {
                FSDirectory indexDir = FSDirectory.open(new File(indexPath).toPath());
                IndexWriterConfig writerConfig = new IndexWriterConfig(new MorfologikAnalyzer());
                indexWriter = new IndexWriter(indexDir, writerConfig);
            }
        } catch (IOException e) {
            throw new AppException("can not get index", e);
        }
        return indexWriter;
    }
}
