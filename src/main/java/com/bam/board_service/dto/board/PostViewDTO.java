package com.bam.board_service.dto.board;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시글 상세 조회를 위한 정보를 갖는 DTO
 * @author bam
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostViewDTO {
    private UUID id;
    private String writer;
    private String title;
    private String contents;
    private Long views;
    private String createdTime;
    private String updatedTime;
}
