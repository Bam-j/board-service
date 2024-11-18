package com.bam.board_service.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Deprecated
public class CommentDTO {
    private UUID id;
    private String writer;
    private String comment;
    private LocalDateTime createdTime;
}
