package com.example.lab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private long id;
    private String authorEmail;
    private ReportType type;
    private int elemId;   
    private String description;

    boolean isReviewed = false;

    public enum ReportType {
        HOUSING,
        REVIEW
    }
}
