package com.github.torczuk.sherlock.infrastructure.query.factory;

import com.github.torczuk.sherlock.domain.query.model.Result;
import org.apache.lucene.document.Document;

public class ResultFactory {

    public Result create(Document document) {
        document.get("url");
        return null;
    }

}
