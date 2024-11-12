package com.bam.board_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_table")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Builder
    public PostEntity(UUID id, String writer, String title, String contents, Long views) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.views = views;
    }
}