package com.bam.board_service.dto.board;

import java.time.LocalDateTime;

public class PostListDTO {
    private String writer;
    private String title;
    private String contents;
    private Long views;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
