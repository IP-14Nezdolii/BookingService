package com.example.lab.entity.post;

import lombok.Value;

@Value
public class Address {
    long streetNumber;
    String street;   
    String city;
    String state;
    String postalCode; 
}
