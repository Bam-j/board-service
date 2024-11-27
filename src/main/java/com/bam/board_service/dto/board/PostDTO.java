package com.bam.board_service.dto.board;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 계층간 게시글 데이터를 교환하기 위한 객체
 * @author bam
 * @version 1.0
 * @deprecated 상황에 따른 DTO들로 분할되어 더 이상 모든 정보를 갖는 DTO는 사용하지 않음.
 */
@Deprecated
@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
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
