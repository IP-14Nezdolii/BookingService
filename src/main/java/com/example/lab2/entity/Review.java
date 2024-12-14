package com.example.lab2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "review_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id = null;

    @Column(name = "author_id")
    private Long authorId;

    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "post_id")
    private Long postId = null;

    @Column(name = "booking_id")
    private Long bookingId;
    
    @Column(name = "author_name")
    private String authorName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Byte rate;
}
