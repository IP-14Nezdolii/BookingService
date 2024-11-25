package com.example.lab.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.lab.entity.Report;
import com.example.lab.repository.interfaces.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
   private final ReportRepository reportRepository;

   public List<Report> readAll() {
        return reportRepository.getAll();
    }

    public Optional<Report> readReport(long id) {
        return reportRepository.getById(id);
    }

    public Optional<Report> createReport(Report report) {
        return reportRepository.save(report);
    }

    public boolean setReportReviewed(long reportId) {
        return reportRepository.setReviewed(reportId);
    }

   public boolean deleteReport(long id) {
        return reportRepository.deleteById(id);
   }
}
