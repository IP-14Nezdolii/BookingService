package com.example.lab.entity.post;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private long id;
    @NonNull 
    private Housing housing;
    private boolean isAvailable;
    private float costPerDay;
    
    private LocalDate minDate;
    private LocalDate maxDate;
}
