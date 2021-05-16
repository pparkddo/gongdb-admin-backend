package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.entity.Subject;
import com.gongdb.admin.announcement.repository.SubjectRepository;
import com.gongdb.admin.announcement.service.SubjectService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubjectServiceTest {
    
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        subjectService.create(Subject.builder().name(alreadyExistsName).build());

        subjectService.getOrCreate(alreadyExistsName);
        assertEquals(subjectRepository.count(), 1);

        String newName = "name2";
        subjectService.getOrCreate(newName);
        assertEquals(subjectRepository.count(), 2);

        subjectService.getOrCreate(alreadyExistsName);
        subjectService.getOrCreate(newName);
        assertEquals(subjectRepository.count(), 2);
    }
}
