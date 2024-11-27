package com.bam.board_service.mapper;

import com.bam.board_service.dto.board.PostEditDTO;
import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;

/**
 * PostDTO와 PostEntity간의 변환을 수행하는 매퍼 클래스
 * @author bam
 * @version 1.0
 */
public class PostMapper {

    /**
     * 게시글 목록 조회를 위해 PostEntity를 PostListDTO로 변환하는 매퍼 메소드
     * @param postEntity DB에서 조회되어 전달된 PostEntity
     * @return PostListDTO
     */
    public PostListDTO toPostListDTO(PostEntity postEntity) {
        PostListDTO postListDTO = PostListDTO.builder()
            .id(postEntity.getId())
            .writer(postEntity.getWriter())
            .title(postEntity.getTitle())
            .contents(postEntity.getContents())
            .views(postEntity.getViews())
            .createdTime(postEntity.getCreatedTime())
            .build();

        return postListDTO;
    }

    /**
     * 게시글 작성 폼에서 입력된 PostWriteDTO를 DB에 저장하기 위한 PostEntity로 변환하는 매퍼 메소드
     * @param postWriteDTO 게시글 작성 폼을 토대로 생성된 DTO
     * @return PostEntity
     */
    public PostEntity toPostEntity(PostWriteDTO postWriteDTO) {
        PostEntity postEntity = PostEntity.builder()
            .writer(postWriteDTO.getWriter())
            .title(postWriteDTO.getTitle())
            .contents(postWriteDTO.getContents())
            .views(0L)
            .build();

        return postEntity;
    }

    /**
     * 게시글 수정 폼을 통해 입력받은 PostEditDTO를 PostEntity로 변환하는 매퍼 메소드
     * <p>
     *     변경할 수 없는 id, writer, views를 원본의 내용인 originalPostEntity의 내용을 담고, <br>
     *     변경 가능성이 있는 title, contents의 내용만 PostEditDTO로 부터 받아와서 Entity로 변환한다.
     * </p>
     * @param originalPostEntity 수정 대상 게시글 Entity
     * @param postEditDTO 수정 폼에 입력된 정보를 토대로 생성된 DTO
     * @return PostEntity
     */
    public PostEntity toPostEntity(PostEntity originalPostEntity, PostEditDTO postEditDTO) {
        PostEntity postEntity = PostEntity.builder()
            .id(originalPostEntity.getId())
            .writer(originalPostEntity.getWriter())
            .title(postEditDTO.getTitle())
            .contents(postEditDTO.getContents())
            .views(originalPostEntity.getViews())
            .build();

        return postEntity;
    }
}
