package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.Certificate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
}
