package com.example.lab.entity.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.lab.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {

             private long postId;
    @NonNull private String userId; 
    @NonNull private LocalDate startDate;
    @NonNull private LocalDate endDate;

    @JsonIgnore
    public long getDays() {
        return ChronoUnit.DAYS.between(this.startDate, this.endDate);
    }

    public boolean isDataConsistencyTo(Post post) {
        return  startDate.compareTo(post.getMinDate()) >= 0 &&  
                endDate.compareTo(post.getMaxDate()) <= 0 &&
                ChronoUnit.DAYS.between(startDate, endDate) > 0;
    }
}
