package com.gongdb.admin.announcement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.repository.PositionRepository;
import com.gongdb.admin.announcement.service.PositionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PositionServiceTest {
    
    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionRepository positionRepository;

    @Test
    public void getOrCreateTest() {
        String alreadyExistsName = "name1";
        positionService.create(Position.builder().name(alreadyExistsName).build());

        positionService.getOrCreate(alreadyExistsName);
        assertEquals(positionRepository.count(), 1);

        String newName = "name2";
        positionService.getOrCreate(newName);
        assertEquals(positionRepository.count(), 2);

        positionService.getOrCreate(alreadyExistsName);
        positionService.getOrCreate(newName);
        assertEquals(positionRepository.count(), 2);
    }
}
