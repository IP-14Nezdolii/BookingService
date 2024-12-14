package com.example.lab2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab2.entity.Report;
import com.example.lab2.security.UserDetailsImpl;
import com.example.lab2.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {

        if (!(report.getReviewId() == null) != (report.getPostId() == null))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Authentication authentication = 
            SecurityContextHolder.getContext().getAuthentication();

        final Long userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        report.setAuthorId(userId);

        return reportService.createReport(report)
           .map(newReport -> new ResponseEntity<>(newReport, HttpStatus.OK))
           .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }
}
