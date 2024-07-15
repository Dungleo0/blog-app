package com.example.blogapp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto implements Serializable {
    @NotBlank(message = "post title must be not blank")
    @Size(max = 100, min = 10)
    private String title;

    @NotBlank(message = "post content must be not blank")
    @Size(max = 100, min = 10)
    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;
    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();

}
