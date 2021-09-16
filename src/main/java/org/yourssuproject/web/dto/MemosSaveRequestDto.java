package org.yourssuproject.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yourssuproject.domain.memos.Memos;

@Getter
@NoArgsConstructor
public class MemosSaveRequestDto {
    private String title;
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
