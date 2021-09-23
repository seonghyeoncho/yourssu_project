package org.yourssuproject.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yourssuproject.domain.memos.Memos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class MemosResponseDto {
    private Long id;
    private String title;
    private String text;
    private String createdAt;
    private String updatedAt;

    public MemosResponseDto(Memos entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.text = entity.getText();
        this.createdAt = entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = entity.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
