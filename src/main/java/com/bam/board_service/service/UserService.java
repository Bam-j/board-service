package com.bam.board_service.service;

import com.bam.board_service.dto.user.UserActiveDTO;
import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.dto.user.UserListDTO;
import com.bam.board_service.dto.user.UserLoginDTO;
import com.bam.board_service.dto.user.UserUpdateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * User 요청과 관련된 로직을 수행하는 서비스 클래스
 * @author bam
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * DB에 유저 정보를 저장하는 메소드
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
                .userType(userEntity.getUserType())
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
     * 사용자 nickname 변경을 처리하는 메소드
     * <p>
     * DB에 이미 존재하거나, 기존 nickname과 동일한 변경을 요청하는 경우 null 반환 변경에 성공한 경우 변경된 정보가 담긴 UserActiveDTO 반환
     * </p>
     *
     * @param id
     * @param userUpdateDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO updateNickname(UUID id, UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

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
                .id(originalUserEntity.getId())
                .nickname(userUpdateDTO.getNickname())
                .userType(originalUserEntity.getUserType())
                .loginState(1L)
                .build();

            return userActiveDTO;
        } else {
            return null;
        }
    }

    /**
     * 사용자 password 변경을 처리하는 메소드
     * <p>
     * 기존 password와 동일한 변경을 요청하는 경우 null 반환 변경에 성공한 경우 변경된 정보가 담긴 UserActiveDTO 반환
     * </p>
     *
     * @param id
     * @param userUpdateDTO
     * @return null or UserActiveDTO
     */
    public UserActiveDTO updatePassword(UUID id, UserUpdateDTO userUpdateDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

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
                .id(originalUserEntity.getId())
                .nickname(userUpdateDTO.getNickname())
                .userType(originalUserEntity.getUserType())
                .loginState(1L)
                .build();

            return userActiveDTO;
        } else {
            return null;
        }
    }

    /**
     * 사용자 계정 삭제를 처리하는 메소드
     * @param id
     * @return index.html
     */
    public String delete(UUID id) {
        //TODO: id 검증 작업
        userRepository.deleteById(id);

        return "redirect:/index";
    }

    /**
     * 사용자 전체 목록 조회를 처리하는 메소드
     * @return List<UserListDTO>
     */
    public List<UserListDTO> findAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserListDTO> userListDTOList = new ArrayList<>();
        UserMapper userMapper = new UserMapper();

        for (UserEntity userEntity : userEntityList) {
            userListDTOList.add(userMapper.toUserListDTO(userEntity));
        }

        return userListDTOList;
    }
}
