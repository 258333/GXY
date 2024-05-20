package com.example.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Category {

    @NotNull
    private Integer id;

    @NotNull
    private String categoryName;

    @NotNull
    private Integer categoryNum;
}
