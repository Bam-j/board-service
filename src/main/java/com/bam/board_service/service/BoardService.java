package com.bam.board_service.service;

import com.bam.board_service.dto.board.PostListDTO;
import com.bam.board_service.dto.board.PostWriteDTO;
import com.bam.board_service.entity.PostEntity;
import com.bam.board_service.mapper.PostMapper;
import com.bam.board_service.repository.BoardRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(PostWriteDTO postWriteDTO) {
        PostMapper postMapper = new PostMapper();
        PostEntity postEntity = postMapper.toPostEntity(postWriteDTO);
        boardRepository.save(postEntity);
    }

    @Transactional
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
}