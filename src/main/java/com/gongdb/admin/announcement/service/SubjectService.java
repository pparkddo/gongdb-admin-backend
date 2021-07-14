package com.gongdb.admin.announcement.service;

import java.util.List;

import com.gongdb.admin.announcement.dto.request.SubjectUpdateDto;
import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.SubjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    public Subject getOrCreate(String name) {
        return subjectRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Subject.builder().name(name).build()));
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void update(Long id, SubjectUpdateDto dto) {
        Subject subject = subjectRepository.findById(id).orElseThrow();
        subject.rename(dto.getName());
    }

    @Transactional(readOnly = true)
    public List<Subject> search(String name) {
        return subjectRepository.findByNameContaining(name);
    }
}
