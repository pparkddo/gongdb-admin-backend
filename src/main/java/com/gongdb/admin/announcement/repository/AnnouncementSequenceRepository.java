package com.gongdb.admin.announcement.repository;

import java.util.Optional;

import com.gongdb.admin.announcement.entity.AnnouncementSequence;
import com.gongdb.admin.announcement.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementSequenceRepository extends JpaRepository<AnnouncementSequence, Long> {

    public Optional<AnnouncementSequence> findByCompanyAndSequence(Company company, String sequence);

}
