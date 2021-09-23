package org.yourssuproject.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yourssuproject.domain.memos.Memos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MemosSaveRequestDto {

    @NotNull(message = "title must not be null")
    private String title;

    @NotNull(message = "text must not be null")
    private String text;

    @Builder
    public MemosSaveRequestDto(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Memos toEntity() {
        return Memos.builder()
                .title(title)
                .text(text)
                .build();
    }
}
