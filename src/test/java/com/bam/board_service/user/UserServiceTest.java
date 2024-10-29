package com.bam.board_service.user;

import com.bam.board_service.dto.UserDTO;
import com.bam.board_service.entity.UserEntity;
import com.bam.board_service.mapper.UserMapper;
import com.bam.board_service.repository.UserRepository;
import com.bam.board_service.service.UserService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("생성된 데이터가 DB에 제대로 save가 되는지 테스트")
    void userSaveTest() {
        //given
        UserDTO userDTO = new UserDTO("tester1", "tester1", "1234");
        UserMapper userMapper = new UserMapper();

        //when
        UserEntity userEntity = userMapper.userDTOToUserEntity(userDTO);
        userRepository.save(userEntity);

        //then
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userEntity.getId());

        optionalUserEntity.ifPresent(entity -> assertEquals(userEntity, entity));
    }
}