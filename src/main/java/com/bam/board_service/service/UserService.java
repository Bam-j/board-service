package com.bam.board_service.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
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
     * View에서 DTO로 정보를 받아 Entity로 변환 후 JPA의 save()를 호출해서 유저 정보를 DB에 저장한다.
     * </p>
     *
     * @param userCreateDTO
     */
    public void save(UserCreateDTO userCreateDTO) {
        UserMapper userMapper = new UserMapper();
        UserEntity userEntity = userMapper.toUserEntity(userCreateDTO);
        userRepository.save(userEntity);
    }

    public UserActiveDTO login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> findByUsername = userRepository.findByUsername(
            userLoginDTO.getUsername());

        if (findByUsername.isEmpty()) {
            return null;
        }

        UserEntity userEntity = findByUsername.get();

        if (Objects.equals(userEntity.getPassword(), userLoginDTO.getPassword())) {
            //password가 일치하는 경우
            UserActiveDTO userActiveDTO = UserActiveDTO.builder()
                .nickname(userEntity.getNickname())
                .loginState(1L)
                .build();

            return userActiveDTO;
        } else {
            return null;
        }
    }
}
