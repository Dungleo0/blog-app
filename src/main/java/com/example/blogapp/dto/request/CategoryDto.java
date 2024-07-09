package com.example.blogapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "categoryTitle must be not blank")
    @Size(min = 4)
    private String categoryTitle;

    @NotBlank(message = "categoryDescription must be not blank")
    private String categoryDescription;

}
