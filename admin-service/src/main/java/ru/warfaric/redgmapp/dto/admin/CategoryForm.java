package ru.warfaric.redgmapp.dto.admin;

import jakarta.validation.constraints.NotBlank;

public class CategoryForm {

    private Long id;

    @NotBlank(message = "Название обязательно")
    private String name;

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
}
