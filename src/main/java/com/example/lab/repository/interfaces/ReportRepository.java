package com.example.lab.repository.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.lab.entity.Report;

public interface ReportRepository {
    public List<Report> getAll();

    public Optional<Report> getById(long id);
    public Optional<Report> save(Report report);
    public Optional<Report> update(Report report);

    public boolean deleteById(long id);
    public boolean setReviewed(long reportId);
}
