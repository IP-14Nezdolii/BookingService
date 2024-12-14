package com.example.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lab2.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
