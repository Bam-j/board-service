package com.bam.board_service.dto.board;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String views;
    private String createdTime;
    private String updatedTime;
}
