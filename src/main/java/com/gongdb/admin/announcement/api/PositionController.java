package com.gongdb.admin.announcement.api;

import javax.validation.Valid;

import com.gongdb.admin.announcement.dto.PositionUpdateDto;
import com.gongdb.admin.announcement.service.PositionService;
import com.gongdb.admin.global.dto.SimpleMessageResponse;

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
    public SimpleMessageResponse updatePosition(
            @PathVariable Long id, @Valid @RequestBody PositionUpdateDto positionUpdateDto) {
        positionService.update(id, positionUpdateDto);
        return SimpleMessageResponse.of("정상적으로 수정되었습니다");
    }
}
