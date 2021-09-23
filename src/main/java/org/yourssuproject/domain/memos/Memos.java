package org.yourssuproject.domain.memos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yourssuproject.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Memos extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Builder
    public Memos (String title, String text) {
        this.title = title;
        this.text = text;
    }

    public void update(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
