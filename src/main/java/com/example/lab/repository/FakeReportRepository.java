package com.example.lab.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab.entity.Report;
import com.example.lab.repository.interfaces.ReportRepository;

@Repository
public class FakeReportRepository implements ReportRepository{
    private final static Map<Long, Report> REPORTS = new HashMap<>();
    private static long idCounter = 0;

    @Override
    public List<Report> getAll() {
        return new ArrayList<Report>(REPORTS.values());
    }

    @Override
    public Optional<Report> getById(long id) {
        return Optional.ofNullable(REPORTS.get(id));
    }

    @Override
    public Optional<Report> save(Report report) {
        report.setId(idCounter);
        if(REPORTS.putIfAbsent(report.getId(), report) == null) {
            idCounter++;
            return Optional.ofNullable(report);
        }   
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Report> update(Report report) {
        if(REPORTS.containsKey(report.getId())) {
            REPORTS.put(report.getId(), report);
            return Optional.ofNullable(report);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public boolean setReviewed(long reportId) {
        Optional<Report> report = getById(reportId);
        if (report.isPresent()) {
            var newReport = report.get();
            newReport.setReviewed(true);
            update(newReport);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return REPORTS.remove(id) != null;
    }
}
