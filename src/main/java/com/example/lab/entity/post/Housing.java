package com.example.lab.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Housing {
    @NonNull private String ownerId;
    @NonNull private String title;   
    private String description;
    
    private short totalArea;
    @NonNull private Address address;
    @NonNull private HousingType type; 

    public enum HousingType {
        ROOM,
        APARTMENT,
        HOUSE
    }
}
