package com.github.andreendo.avalexemplo.q5.repositories;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TopicRepository {

    public List<String> getTopics() {
        return Arrays.asList(
                "Clima",
                "Comércio",
                "Indústria",
                "Governo"
        );
    }

}
