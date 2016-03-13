package com.github.torczuk.sherlock.domain.query.repository;

import com.github.torczuk.sherlock.domain.query.model.Result;

import java.util.List;
import java.util.Set;

public interface ResultReadRepository {

    List<Result> find(Set<String> keyWords);

}
