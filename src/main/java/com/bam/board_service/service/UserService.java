package com.bam.board_service.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 로그인 기능을 수행하는 메소드
     * <p>
     *     폼에 입력한 username와 DB의 username 컬럼을 탐색한다.
     *     useraname과 passoword를 비교하여, 둘 다 일치하는 경우에만 홈페이지 활동에 사용되는
     *     UserActiveDTO를 반환한다. 그렇지 않은경우 null을 반환
     * </p>
     * @param userLoginDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> findByUsername = userRepository.findByUsername(
            userLoginDTO.getUsername());

        if (findByUsername.isEmpty()) {
            //입력한 username이 DB에 없어서 조회결과가 empty(null)인 경우
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
            //password가 일치하지 않는 경우
            return null;
        }
    }

    /**
     * 사용자 정보 수정을 위한 기능을 수행하는 메소드
     * <p>
     *     nickname 변경시 findByNickname을 통해 이미 존재하는 nickname인 경우 null을 반환하고,
     *     존재하지 않는 경우 DB에 저장한 뒤 변경된 내용을 기반으로 UserActiveDTO를 반환한다. <br>
     *     password 변경시 기존 패스워드와 동일한 값을 입력하면 null을 반환하고,
     *     기존 패스워드와 다른 경우 DB에 저장한 뒤 변경된 내용을 기반으로 UserActiveDTO를 반환한다.
     * </p>
     * @param username
     * @param userUpdateDTO
     * @return null or UserActiveDTO
     */
    /*
    public UserActiveDTO update(String username, UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> optionalFindByUsernameUserEntity = userRepository.findByUsername(username);
        UserEntity originalUserEntity;

        if (optionalFindByUsernameUserEntity.isPresent()) {
            originalUserEntity = optionalFindByUsernameUserEntity.get();
        } else {
            return null;
        }

        //TODO: 두 개의 케이스 분리
        if (originalUserEntity.getNickname().equals(userUpdateDTO.getNickname())) {
            return null;
        }

        if (originalUserEntity.getPassword().equals(userUpdateDTO.getPassword())) {
            return null;
        }

        UserMapper userMapper = new UserMapper();
        UserEntity updatedUserEntity = userMapper.toUserEntity(originalUserEntity, userUpdateDTO);
        userRepository.save(updatedUserEntity);

        UserActiveDTO userActiveDTO = UserActiveDTO.builder()
            .nickname(userUpdateDTO.getNickname())
            .loginState(1L)
            .build();

        return userActiveDTO;
    }
     */
}
