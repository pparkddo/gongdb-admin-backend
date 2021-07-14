package com.gongdb.admin.announcement.converter;

import com.gongdb.admin.announcement.dto.request.SearchType;

import org.springframework.core.convert.converter.Converter;

public class StringToSearchTypeConverter implements Converter<String, SearchType> {

    @Override
    public SearchType convert(String source) {
        return SearchType.valueOf(source.toUpperCase());
    }
}
