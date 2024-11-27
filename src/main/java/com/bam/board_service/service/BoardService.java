package com.bam.board_service.service;

import com.bam.board_service.dto.board.PostEditDTO;
import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.board.PostViewDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.mapper.PostMapper;
import com.bam.board_service.repository.BoardRepository;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Posts 요청과 관련된 로직을 수행하는 서비스 클래스
 * @author bam
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 저장을 처리하는 메소드
     * @param postWriteDTO
     * @return UUID
     */
    public UUID save(PostWriteDTO postWriteDTO) {
        PostMapper postMapper = new PostMapper();
        PostEntity postEntity = postMapper.toPostEntity(postWriteDTO);
        boardRepository.save(postEntity);

        return postEntity.getId();
    }

    /**
     * 게시글 전체 목록을 조회하는 메소드
     * @return List<PostListDTO>
     */
    @Transactional(readOnly = true)
    public List<PostListDTO> findAllPosts() {
        List<PostEntity> postEntityList = boardRepository.findAll();
        List<PostListDTO> postListDTOList = new ArrayList<>();
        PostMapper postMapper = new PostMapper();

        for (PostEntity postEntity : postEntityList) {
            postListDTOList.add(postMapper.toPostListDTO(postEntity));
        }

        return postListDTOList;
    }

    /**
     * 게시글 조회시 해당 게시글의 조회수를 1 증가시키는 메소드
     * @param id
     */
    @Transactional
    public void increaseViews(UUID id) {
        boardRepository.increaseViews(id);
    }

    /**
     * 게시글 상세 조회를 처리하는 메소드
     * @param id
     * @return PostViewDTO
     */
    @Transactional(readOnly = true)
    public PostViewDTO findById(UUID id) {
        Optional<PostEntity> optionalPostEntity = boardRepository.findById(id);

        if (optionalPostEntity.isEmpty()) {
            return null;
        }

        PostEntity postEntity = optionalPostEntity.get();
        PostViewDTO postViewDTO = PostViewDTO.builder()
            .id(id)
            .writer(postEntity.getWriter())
            .title(postEntity.getTitle())
            .contents(postEntity.getContents())
            .views(postEntity.getViews())
            .build();

        return postViewDTO;
    }

    /**
     * 게시글 수정을 처리하는 메소드
     * @param postEditDTO
     * @return Boolean
     */
    public Boolean edit(PostEditDTO postEditDTO) {
        String title = postEditDTO.getTitle();

        if (title == null || title.trim().isEmpty()) {
            //제목이 없거나 빈 내용이면 수정 x
            return false;
        }

        UUID postId = postEditDTO.getId();
        PostEntity originalPostEntity = boardRepository.findById(postId).get();
        PostMapper postMapper = new PostMapper();

        PostEntity editedPostEntity = postMapper.toPostEntity(originalPostEntity, postEditDTO);
        boardRepository.save(editedPostEntity);

        return true;
    }

    /**
     * 게시글 삭제를 처리하는 메소드
     * @param id
     */
    public void delete(UUID id) {
        boardRepository.deleteById(id);
    }
}