package com.bam.board_service.board.service;

import com.bam.board_service.dto.board.PostEditDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.mapper.PostMapper;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.service.BoardService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostEditServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    private UUID id;

    @BeforeEach
    void setUp() {
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        id = boardService.save(postWriteDTO);
    }

    @Test
    @DisplayName("게시글 수정 요청 시 PostEntity에 updatedTime이 잘 생성됐는지 확인")
    void createUpdatedTimeInPostEntityTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        boardRepository.save(postMapper.toPostEntity(postEditDTO));

        //then
        PostEntity postEntity = boardRepository.findById(id).get();

        assertNotNull(postEntity.getUpdatedTime());
        assertNotEquals(postEntity.getCreatedTime(), postEntity.getUpdatedTime());
    }

    @Test
    @DisplayName("게시글의 제목만 수정했을 때 반영 여부 테스트")
    void postTitleEditedServiceTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        boardRepository.save(postMapper.toPostEntity(postEditDTO));

        //then
        PostEntity postEntity = boardRepository.findById(id).get();

        assertAll(
            () -> assertNotEquals(postEntity.getTitle(), "tester"),
            () -> assertEquals(postEntity.getTitle(), "edited title"),
            () -> assertEquals(postEntity.getContents(), "test")
        );
    }

    @Test
    @DisplayName("게시글의 내용만 수정했을 때 반영 여부 테스트")
    void postContentsEditedServiceTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("test")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        boardRepository.save(postMapper.toPostEntity(postEditDTO));

        //then
        PostEntity postEntity = boardRepository.findById(id).get();

        assertAll(
            () -> assertEquals(postEntity.getTitle(), "edited title"),
            () -> assertNotEquals(postEntity.getContents(), "test"),
            () -> assertEquals(postEntity.getContents(), "edited contents")
        );
    }

    @Test
    @DisplayName("게시글의 제목, 내용을 전부 수정했을 때의 반영 여부 테스트")
    void postEditedServiceTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        boardRepository.save(postMapper.toPostEntity(postEditDTO));

        //then
        PostEntity postEntity = boardRepository.findById(id).get();

        assertAll(
            () -> assertNotEquals(postEntity.getTitle(), "test"),
            () -> assertEquals(postEntity.getTitle(), "edited title"),
            () -> assertNotEquals(postEntity.getContents(), "test"),
            () -> assertEquals(postEntity.getContents(), "edited contents")
        );
    }

    @Test
    @DisplayName("BoardService.update() 실패 테스트 - 빈 제목으로의 수정")
    void boardServiceUpdateMethodFailureTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        Boolean updateResult = boardService.update(id);

        //then
        assertFalse(updateResult);
    }

    @Test
    @DisplayName("BoardService.update() 성공 테스트")
    void boardServiceUpdateMethodSuccessTest() {
        //given
        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        Boolean updateResult = boardService.update(id);

        //then
        assertTrue(updateResult);
    }
}