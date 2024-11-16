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

    /*
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

     */

    @Test
    @DisplayName("게시글 수정 요청 시 PostEntity에 updatedTime이 잘 생성됐는지 확인")
    void createUpdatedTimeInPostEntityTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();
        PostEntity originalPostEntity = boardRepository.findById(id).get();

        //when
        boardRepository.save(postMapper.toPostEntity(originalPostEntity, postEditDTO));

        //then
        PostEntity updatedPostEntity = boardRepository.findById(id).get();

        assertNotNull(updatedPostEntity.getUpdatedTime());
        assertNotEquals(updatedPostEntity.getCreatedTime(), updatedPostEntity.getUpdatedTime());
    }

    @Test
    @DisplayName("게시글의 제목만 수정했을 때 반영 여부 테스트")
    void postTitleEditedServiceTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();
        PostEntity originalPostEntity = boardRepository.findById(id).get();

        //when
        boardRepository.save(postMapper.toPostEntity(originalPostEntity, postEditDTO));

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
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("test")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();
        PostEntity originalPostEntity = boardRepository.findById(id).get();

        //when
        boardRepository.save(postMapper.toPostEntity(originalPostEntity, postEditDTO));

        //then
        PostEntity updatedPostEntity = boardRepository.findById(id).get();

        assertAll(
            () -> assertEquals(updatedPostEntity.getTitle(), "test"),
            () -> assertNotEquals(updatedPostEntity.getContents(), "test"),
            () -> assertEquals(updatedPostEntity.getContents(), "edited contents")
        );
    }

    @Test
    @DisplayName("게시글의 제목, 내용을 전부 수정했을 때의 반영 여부 테스트")
    void postEditedServiceTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();
        PostEntity originalPostEntity = boardRepository.findById(id).get();

        //when
        boardRepository.save(postMapper.toPostEntity(originalPostEntity, postEditDTO));

        //then
        PostEntity updatedPostEntity = boardRepository.findById(id).get();

        assertAll(
            () -> assertNotEquals(updatedPostEntity.getTitle(), "test"),
            () -> assertEquals(updatedPostEntity.getTitle(), "edited title"),
            () -> assertNotEquals(updatedPostEntity.getContents(), "test"),
            () -> assertEquals(updatedPostEntity.getContents(), "edited contents")
        );
    }

    @Test
    @DisplayName("BoardService.update() 실패 테스트 - 빈 제목으로의 수정")
    void boardServiceUpdateMethodFailureTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .id(id)
            .title("")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        Boolean updateResult = boardService.edit(postEditDTO);

        //then
        assertFalse(updateResult);
    }

    @Test
    @DisplayName("BoardService.update() 성공 테스트")
    void boardServiceUpdateMethodSuccessTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        PostEditDTO postEditDTO = PostEditDTO.builder()
            .id(id)
            .title("edited title")
            .contents("edited contents")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        Boolean updateResult = boardService.edit(postEditDTO);

        //then
        assertTrue(updateResult);
    }
}