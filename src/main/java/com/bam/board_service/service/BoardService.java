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

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public UUID save(PostWriteDTO postWriteDTO) {
        PostMapper postMapper = new PostMapper();
        PostEntity postEntity = postMapper.toPostEntity(postWriteDTO);
        boardRepository.save(postEntity);

        return postEntity.getId();
    }

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

    @Transactional
    public void increaseViews(UUID id) {
        boardRepository.increaseViews(id);
    }

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
}