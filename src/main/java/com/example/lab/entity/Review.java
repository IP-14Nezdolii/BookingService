package com.example.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private long id;
    private String authorId;
    private long postId;
    private String authorName;
    private String description;
    private byte rate;
}
