package com.bam.board_service.dto.comment;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 계층간 댓글 데이터를 교환하기 위한 객체
 * @author bam
 * @version 1.0
 * @deprecated 상황에 따른 DTO들로 분할되어 더 이상 모든 정보를 갖는 DTO는 사용하지 않음.
 */
@Deprecated
@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private UUID id;
    private String writer;
    private String comment;
    private LocalDateTime createdTime;
}
