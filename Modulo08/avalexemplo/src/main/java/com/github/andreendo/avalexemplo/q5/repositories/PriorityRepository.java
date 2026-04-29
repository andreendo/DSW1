package com.github.andreendo.avalexemplo.q5.repositories;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class PriorityRepository {

    public List<String> getPriorities() {
        return Arrays.asList(
                "high",
                "medium",
                "low"
        );
    }

}
