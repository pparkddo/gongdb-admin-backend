package com.gongdb.admin.announcement.service;

import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.repository.PositionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService {
    
    private final PositionRepository positionRepository;

    @Transactional
    public Position getOrCreate(String name) {
        return positionRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Position.builder().name(name).build()));
    }

    public Position create(Position position) {
        return positionRepository.save(position);
    }
}
