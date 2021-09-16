package org.yourssuproject.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemosUpdateRequestDto {
    private String title;
    private String text;

    @Builder
    public MemosUpdateRequestDto(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
