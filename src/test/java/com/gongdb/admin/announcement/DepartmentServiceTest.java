package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.gongdb.admin.announcement.dto.request.DepartmentUpdateDto;
import com.gongdb.admin.announcement.dto.response.DepartmentDto;
import com.gongdb.admin.announcement.entity.Department;
import com.gongdb.admin.announcement.repository.DepartmentRepository;
import com.gongdb.admin.announcement.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
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

    @Test
    public void getAllTest() {
        List.of("name1", "name2").stream().forEach(departmentService::getOrCreate);

        List<DepartmentDto> departments = departmentService.getAll();
        assertEquals(departments.size(), 2);
        assertTrue(departments.stream().map(each -> each.getName()).collect(Collectors.toList()).contains("name1"));
    }

    @Test
    public void updateTest() {
        String beforeUpdateName = "beforeUpdate";
        Department department
            = departmentService.create(Department.builder().name(beforeUpdateName).build());

        String afterUpdateName = "renamed";
        DepartmentUpdateDto dto = DepartmentUpdateDto.builder().name(afterUpdateName).build();
        departmentService.update(department.getId(), dto);
        departmentRepository.flush();
        
        assertEquals(afterUpdateName, department.getName());
    }
}
