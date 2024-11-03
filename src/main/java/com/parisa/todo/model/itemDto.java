package com.parisa.todo.model;

import jakarta.validation.constraints.NotEmpty;

public class itemDto {
    @NotEmpty(message = "The description is required")
    String title;

    String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
