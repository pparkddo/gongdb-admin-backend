package com.gongdb.admin.announcement.api;

import java.util.List;

import com.gongdb.admin.announcement.dto.request.SearchType;
import com.gongdb.admin.announcement.dto.response.SearchResult;
import com.gongdb.admin.announcement.service.SearchService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    
    @GetMapping
    public List<SearchResult> search(@RequestParam(value = "query") String query,
            @RequestParam(value = "type", required = false, defaultValue = "ALL") SearchType type) {
        return searchService.search(query, type);
    }
}
