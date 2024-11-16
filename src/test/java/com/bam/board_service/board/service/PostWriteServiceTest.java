package com.bam.board_service.board.service;

import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.mapper.PostMapper;
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
@Transactional
class PostWriteServiceTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("생성된 post 데이터가 정상적으로 DB에 save되는지 테스트")
    void createPostAndSaveDBTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();
        PostMapper postMapper = new PostMapper();

        //when
        PostEntity postEntity = postMapper.toPostEntity(postWriteDTO);
        boardRepository.save(postEntity);

        //then
        Optional<PostEntity> optionalPostEntity = boardRepository.findById(postEntity.getId());

        assertTrue(optionalPostEntity.isPresent());
    }

    @Test
    @DisplayName("PostService.save() 동작 테스트")
    void postServiceSaveMethodTest() {
        //given
        PostWriteDTO postWriteDTO = PostWriteDTO.builder()
            .writer("tester")
            .title("test")
            .contents("test")
            .build();

        //when
        UUID id = boardService.save(postWriteDTO);

        //then
        PostEntity postEntity = boardRepository.findById(id).get();
        assertThat(postEntity.getWriter()).isEqualTo("tester");
        assertThat(postEntity.getTitle()).isEqualTo("test");
        assertThat(postEntity.getContents()).isEqualTo("test");
    }
}