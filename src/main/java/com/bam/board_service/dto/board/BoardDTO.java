package com.bam.board_service.dto.board;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Deprecated
@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private UUID id;

    /**
     * writer = user의 nickname
     */
    private String writer;
    private String title;
    private String contents;
    private Long views;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
