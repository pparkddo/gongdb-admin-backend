package com.gongdb.admin.announcement.service;

import java.util.List;

import com.gongdb.admin.announcement.dto.request.PositionUpdateDto;
import com.gongdb.admin.announcement.entity.Position;
import com.gongdb.admin.announcement.repository.PositionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PositionService {
    
    private final PositionRepository positionRepository;

    public Position getOrCreate(String name) {
        return positionRepository 
                    .findByName(name)
                    .orElseGet(() -> create(Position.builder().name(name).build()));
    }

    public Position create(Position position) {
        return positionRepository.save(position);
    }

    public void update(Long id, PositionUpdateDto dto) {
        Position position = positionRepository.findById(id).orElseThrow();
        position.rename(dto.getName());
    }

    @Transactional(readOnly = true)
    public List<Position> search(String name) {
        return positionRepository.findByNameContaining(name);
    }
}
