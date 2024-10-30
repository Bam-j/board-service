package com.bam.board_service.user.service;

import com.bam.board_service.dto.user.UserCreateDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserJoinServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @Test
    @DisplayName("생성된 데이터가 DB에 제대로 save가 되는지 테스트")
    void userJoinServiceTest() {
        //given
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .username("test")
            .nickname("test")
            .password("1234")
            .build();
        UserMapper userMapper = new UserMapper();

        //when
        UserEntity userEntity = userMapper.toUserEntity(userCreateDTO);
        userRepository.save(userEntity);

        //then
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userEntity.getId());

        optionalUserEntity.ifPresent(entity -> assertEquals(userEntity, entity));
    }
}