package com.gongdb.admin.announcement.repository;

import java.util.List;
import java.util.Optional;

import com.gongdb.admin.announcement.entity.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public Optional<Subject> findByName(String name);

    public List<Subject> findByNameContaining(String name);

}

