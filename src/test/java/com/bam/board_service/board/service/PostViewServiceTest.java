package com.bam.board_service.board.service;

import com.bam.board_service.dto.board.PostViewDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.service.BoardService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostViewServiceTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("조회에 성공한 경우 조회수가 1올라가는지 테스트")
    void increaseViewsTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("increase views test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        //when
        boardService.increaseViews(id);

        //then
        PostEntity postEntity = boardRepository.findById(id).get();

        assertThat(postEntity.getViews()).isEqualTo(1L);
    }

    @Test
    @DisplayName("조회 시 원하는 정보가 DTO에 담겨서 반환되는 지 테스트")
    void getPostViewDTOTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test title")
            .contents("can u see me?")
            .build();
        UUID id = boardService.save(postWriteDTO);

        //when
        boardService.increaseViews(id);

        Optional<PostEntity> optionalPostEntity = boardRepository.findById(id);
        PostEntity postEntity = optionalPostEntity.get();

        PostViewDTO postViewDTO = PostViewDTO.builder()
            .id(postEntity.getId())
            .writer(postEntity.getWriter())
            .title(postEntity.getTitle())
            .contents(postEntity.getContents())
            .views(postEntity.getViews())
            .build();

        //then
        assertAll(
            () -> assertThat(postViewDTO.getId()).isEqualTo(id),
            () -> assertThat(postViewDTO.getWriter()).isEqualTo("tester"),
            () -> assertThat(postViewDTO.getTitle()).isEqualTo("test title"),
            () -> assertThat(postViewDTO.getContents()).isEqualTo("can u see me?"),
            () -> assertThat(postViewDTO.getViews()).isEqualTo(1L)
        );
    }
}