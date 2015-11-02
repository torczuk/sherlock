package com.github.torczuk.sherlock.infrastructure.query.repository;

import com.github.torczuk.sherlock.domain.query.model.Result;
import com.github.torczuk.sherlock.domain.query.repository.ResultReadRepository;
import org.apache.lucene.analysis.morfologik.MorfologikAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class LuceneResultReadRepository implements ResultReadRepository {
    private String pathToIndex;
    private IndexSearcher indexSearcher;

    public LuceneResultReadRepository(String pathToIndex) {
        this.pathToIndex = pathToIndex;
    }

    @Override
    public List<Result> find(Set<String> keyWords) {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        keyWords.stream()
                .map(word -> createQuery(word))
                .forEach(query -> builder.add(query, BooleanClause.Occur.SHOULD));
        try {
            TopDocs search = getIndexSearcher(pathToIndex).search(builder.build(), 10);
            return asList(search.scoreDocs).stream()
                    .map(this::createResult).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Cannot search", e);
        }
    }

    private Result createResult(ScoreDoc scoreDoc) {
        try {
            Document document = indexSearcher.doc(scoreDoc.doc);
            return new Result(document.get("url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Query createQuery(String word) {
        try {
            QueryParser queryParser = new QueryParser("content", new MorfologikAnalyzer());
            return queryParser.parse(word);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private IndexSearcher getIndexSearcher(String pathToIndex) {
        if (indexSearcher == null) {
            DirectoryReader indexDirectory = null;
            try {
                indexDirectory = DirectoryReader.open(FSDirectory.open(new File(pathToIndex).toPath()));
                indexSearcher = new IndexSearcher(indexDirectory);
            } catch (IOException e) {
                if (indexDirectory != null) {
                    try {
                        indexDirectory.close();
                    } catch (IOException closing) {
                        closing.printStackTrace();
                    }
                }
                throw new RuntimeException(String.format("Cannot open dir to read index - %s", pathToIndex, e));
            }
        }
        return indexSearcher;
    }
}
