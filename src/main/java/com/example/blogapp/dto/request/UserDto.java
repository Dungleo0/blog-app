package com.example.blogapp.dto.request;

import jakarta.persistence.Column;
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

public class UserDto {

    private Integer id;

    @NotBlank(message = "username must not be blank")
    @Size(min = 4, max = 20, message = "username must 4 to 20 characters")
    private String name;

    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "email must not be blank")
    private String email;

    @NotBlank(message = "about must not be blank")
    private String about;
}
