package com.gongdb.admin.announcement.repository;

import java.util.List;
import java.util.Optional;

import com.gongdb.admin.announcement.entity.Position;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    
    public Optional<Position> findByName(String name);

    public List<Position> findByNameContaining(String name);

}
