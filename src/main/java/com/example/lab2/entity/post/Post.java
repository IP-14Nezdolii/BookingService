package com.example.lab2.entity.post;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @Builder.Default
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "post_id")  
    private Long id = null;

    @Embedded
    private Housing housing;

    @Column(name = "cost_per_day")
    private Float costPerDay;
    
    @Basic
    @Column(name = "min_date")
    private LocalDate minDate;
    
    @Basic
    @Column(name = "max_date")
    private LocalDate maxDate;

    @Builder.Default
    private Byte score = POST_NOT_SCORED;

    @Column(name = "image_key")
    @JsonIgnore
    @Builder.Default
    private String imageKey = null;

    @JsonIgnore
    @Builder.Default
    @Column(name = "post_status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.AVAILABLE;

    @JsonIgnore
    public Long getOwnerId() {return housing.getOwnerId();}

    public boolean isAvailable() { return postStatus == PostStatus.AVAILABLE;}

    @JsonIgnore
    @Transient
    static public final Byte POST_NOT_SCORED = null;

    public enum PostStatus {
        AVAILABLE, UNAVAILABLE, ON_DELETE
    }
}
