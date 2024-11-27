package com.bam.board_service.dto.board;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시글 목록 조회에 필요한 정보를 갖는 DTO
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostListDTO {
    private UUID id;
    private String writer;
    private String title;
    private String contents;
    private Long views;
    private LocalDateTime createdTime;
}
