package com.gongdb.admin.announcement.repository;

import com.gongdb.admin.announcement.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}

