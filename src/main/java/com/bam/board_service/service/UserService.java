package com.bam.board_service.service;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
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

    public UserDTO login(UserDTO userDTO) {
        Optional<UserEntity> findByUsername = userRepository.findByUsername(userDTO.getUsername());

        if (findByUsername.isEmpty()) {
            return null;
        }

        UserEntity userEntity = findByUsername.get();

        if (userEntity.getPassword().equals(userDTO.getPassword())) {
            //UserDTO dto = UserDTO.toUserDTO(userEntity);

            return null;
        } else {
            return null;
        }
    }
}
