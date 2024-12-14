package com.example.lab2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab2.entity.Report;
import com.example.lab2.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
   private final ReportRepository reportRepository;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReport(long id) {
        return reportRepository.findById(id);
    }

    public Optional<Report> createReport(Report report) {
        if (!(report.getReviewId() == null) != (report.getPostId() == null))
            return Optional.ofNullable(null);

        report.setId(null);
        return Optional.ofNullable(reportRepository.save(report));
    }

    public boolean setReportReviewed(long reportId) {
        Optional<Report> result = reportRepository.findById(reportId);

        if (result.isEmpty())
            return false;

        Report report = result.get();
        report.setIsReviewed(true);
        reportRepository.save(report);

        return true;
    }
    /////////////////////////////////////////////////
    /// Test
    public boolean deleteReport(long id) {
            reportRepository.deleteById(id);
            return true;
    }
}
