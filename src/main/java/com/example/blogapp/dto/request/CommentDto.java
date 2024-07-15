package com.example.blogapp.dto.request;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto implements Serializable {
    private int id;
    private String content;

}
