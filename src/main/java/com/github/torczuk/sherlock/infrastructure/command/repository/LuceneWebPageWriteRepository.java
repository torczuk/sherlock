package com.github.torczuk.sherlock.infrastructure.command.repository;

import com.github.torczuk.sherlock.domain.command.model.WebPage;
import com.github.torczuk.sherlock.domain.command.repository.WebPageWriteRepository;
import com.github.torczuk.sherlock.domain.exception.AppException;
import com.github.torczuk.sherlock.infrastructure.command.factory.DocumentFactory;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class LuceneWebPageWriteRepository implements WebPageWriteRepository {

    private final String indexPath;
    private final DocumentFactory documentFactory;
    private IndexWriter indexWriter;

    @Autowired
    public LuceneWebPageWriteRepository(@Value("${index.dir.path}")String indexPath, DocumentFactory documentFactory) {
        this.indexPath = indexPath;
        this.documentFactory = documentFactory;
    }

    @Override
    public void write(WebPage webPage) {
        try {
            IndexWriter index = getIndex();
            Document doc = documentFactory.create(webPage);
            index.addDocument(doc);
        } catch (IOException e) {
            throw new AppException("can not add field to the index", e);
        }
    }

    @Override
    public void write(Collection<WebPage> webPages) {
        try{
            IndexWriter index = getIndex();
            Set<Document> documents = webPages.stream().map(webPage -> documentFactory.create(webPage)).collect(Collectors.toSet());
            index.addDocuments(documents);
        } catch (IOException e) {
            throw new AppException("can not add field to the index", e);
        }
    }

    @Override
    public void flush() {
        try {
            getIndex().close();
        } catch (IOException e) {
            throw new AppException(e);
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
