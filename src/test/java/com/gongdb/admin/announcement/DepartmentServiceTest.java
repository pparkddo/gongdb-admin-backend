package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.repository.DepartmentRepository;
import com.gongdb.admin.announcement.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentServiceTest {
    
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        departmentService.create(Department.builder().name(alreadyExistsName).build());

        departmentService.getOrCreate(alreadyExistsName);
        assertEquals(departmentRepository.count(), 1);

        String newName = "name2";
        departmentService.getOrCreate(newName);
        assertEquals(departmentRepository.count(), 2);

        departmentService.getOrCreate(alreadyExistsName);
        departmentService.getOrCreate(newName);
        assertEquals(departmentRepository.count(), 2);
    }
}
