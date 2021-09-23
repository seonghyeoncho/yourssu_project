package org.yourssuproject.web.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class MemosUpdateRequestDto {

    @NotNull(message = "title is null")
    private String title;

    @NotNull(message = "text is null")
    private String text;

    @Builder
    public MemosUpdateRequestDto(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
