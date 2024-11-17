package com.bam.board_service.board.service;

import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.repository.BoardRepository;
import com.bam.board_service.service.BoardService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostDeleteServiceTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("삭제 수행 후 조회시 null을 반환하는 지 테스트")
    void returnNullAfterDeleteTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        //when
         boardRepository.deleteById(id);

        //then
        assertNull(boardService.findById(id));
    }

    @Test
    @DisplayName("BoardService.delete() 실패 테스트 - 요청한 id 값이 존재하지 않음")
    void boardServiceFailureByNotExistsIdTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        UUID randomId = UUID.randomUUID();

        //when
        boardService.delete(randomId);

        //then
        assertNotNull(boardService.findById(id));
    }

    @Test
    @DisplayName("BoardService.delete() 성공 테스트")
    void boardServiceSuccessTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        UUID id = boardService.save(postWriteDTO);

        //when
        boardService.delete(id);

        //then
        assertNull(boardService.findById(id));
    }
}