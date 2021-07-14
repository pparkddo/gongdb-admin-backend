package com.gongdb.admin.announcement.repository;

import java.util.List;
import java.util.Optional;

import com.gongdb.admin.announcement.entity.Certificate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
    public Optional<Certificate> findByName(String name);

    public List<Certificate> findByNameContaining(String name);

}
