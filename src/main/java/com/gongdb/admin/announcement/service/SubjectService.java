package com.gongdb.admin.announcement.service;

import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.SubjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    @Transactional
    public Subject getOrCreate(String name) {
        return subjectRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Subject.builder().name(name).build()));
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }
}
