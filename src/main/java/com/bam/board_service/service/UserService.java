package com.bam.board_service.service;

import com.bam.board_service.dto.UserDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * User 요청과 관련된 로직을 수행하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * DB에 유저 정보를 저장
     * <p>
     *     View에서 DTO로 정보를 받아 Entity로 변환 후
     *     JPA의 save()를 호출해서 유저 정보를 DB에 저장한다.
     * </p>
     * @param userDTO
     */
    public void save(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }
}