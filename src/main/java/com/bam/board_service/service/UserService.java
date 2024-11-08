package com.bam.board_service.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
     * 폼에 입력한 username와 DB의 username 컬럼을 탐색한다. useraname과 passoword를 비교하여, 둘 다 일치하는 경우에만 홈페이지 활동에
     * 사용되는 UserActiveDTO를 반환한다. 그렇지 않은경우 null을 반환
     * </p>
     *
     * @param userLoginDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO login(UserLoginDTO userLoginDTO, HttpSession session) {
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
                .id(userEntity.getId())
                .nickname(userEntity.getNickname())
                .loginState(1L)
                .build();

            session.setAttribute("loginUserId", userEntity.getId());

            return userActiveDTO;
        } else {
            //password가 일치하지 않는 경우
            return null;
        }
    }

    /**
     * 사용자 nickname 변경 요청이 들어오면 처리하는 메소드
     * <p>
     * DB에 이미 존재하거나, 기존 nickname과 동일한 변경을 요청하는 경우 null 반환 변경에 성공한 경우 변경된 정보가 담긴 UserActiveDTO 반환
     * </p>
     *
     * @param username
     * @param userUpdateDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO updateNickname(String username, UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isEmpty()) {
            return null;
        }

        UserEntity originalUserEntity = optionalUserEntity.get();

        if (!originalUserEntity.getNickname().equals(userUpdateDTO.getNickname())) {
            UserMapper userMapper = new UserMapper();
            UserEntity updatedUserEntity = userMapper.toUserEntity(originalUserEntity,
                userUpdateDTO);
            userRepository.save(updatedUserEntity);

            UserActiveDTO userActiveDTO = UserActiveDTO.builder()
                .nickname(userUpdateDTO.getNickname())
                .loginState(1L)
                .build();
            return userActiveDTO;
        } else {
            return null;
        }
    }

    /**
     * 사용자 password 변경 요청이 들어오면 처리하는 메소드
     * <p>
     * 기존 password와 동일한 변경을 요청하는 경우 null 반환 변경에 성공한 경우 변경된 정보가 담긴 UserActiveDTO 반환
     * </p>
     *
     * @param username
     * @param userUpdateDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO updatePassword(String username, UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isEmpty()) {
            return null;
        }

        UserEntity originalUserEntity = optionalUserEntity.get();

        if (!originalUserEntity.getPassword().equals(userUpdateDTO.getPassword())) {
            UserMapper userMapper = new UserMapper();
            UserEntity updatedUserEntity = userMapper.toUserEntity(originalUserEntity,
                userUpdateDTO);
            userRepository.save(updatedUserEntity);

            UserActiveDTO userActiveDTO = UserActiveDTO.builder()
                .nickname(userUpdateDTO.getNickname())
                .loginState(1L)
                .build();
            return userActiveDTO;
        } else {
            return null;
        }
    }

    public String delete(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isEmpty()) {
            return null;
        }

        UserEntity userEntity = optionalUserEntity.get();

        userRepository.deleteById(userEntity.getId());

        return "redirect:/index";
    }
}
