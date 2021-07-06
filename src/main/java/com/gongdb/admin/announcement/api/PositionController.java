package com.gongdb.admin.announcement.api;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.PositionUpdateDto;
import com.gongdb.admin.announcement.service.PositionService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class PositionController {
    
    private final PositionService positionService;
    
    @PutMapping("/{id}")
    public Map<String, String> updatePosition(
            @PathVariable Long id, @Valid @RequestBody PositionUpdateDto positionUpdateDto) {
        positionService.update(id, positionUpdateDto);
        return Collections.singletonMap("response", "ok");
    }
}
