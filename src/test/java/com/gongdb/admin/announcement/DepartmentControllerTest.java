package com.gongdb.admin.announcement;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.gongdb.admin.announcement.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class DepartmentControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    private static final String END_POINT = "/api/department";

    @Test
    public void getEmptyDepartmentsTest() throws Exception {
        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void getCertificatesTest() throws Exception {
        List.of("name1", "name2").stream().forEach(departmentService::getOrCreate);

        this.mockMvc
            .perform(get(END_POINT))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }
}