package com.github.userrest.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItemAddForm {
    @NotBlank(message = "name mandatory")
    @Size(min = 3, max = 50, message = "Name between 3 to 5 characters")
    private String name;

    @NotBlank(message = "description mandatory")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
