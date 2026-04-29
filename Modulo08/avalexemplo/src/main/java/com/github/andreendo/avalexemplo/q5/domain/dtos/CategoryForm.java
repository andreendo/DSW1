package com.github.andreendo.avalexemplo.q5.domain.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class CategoryForm {

    private Long id;

    @NotBlank
    private String name;

    private String priority;

    private List<String> topics = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "CategoryForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", topics=" + topics +
                '}';
    }
}
