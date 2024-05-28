package com.example.workouttracker.user.service;

import com.example.workouttracker.core.user.*;
import com.example.workouttracker.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity user1;
    private UserEntity user2;
    private User userDto1;
    private User userDto2;

    @BeforeEach
    public void setUp(){
        user1 = UserEntity.builder()
                .email("email1")
                .firstName("firstName1")
                .lastName("lastName1")
                .password("password1")
                .isTermsAndConditionsAccepted(true)
                .genders(Set.of(UserGender.MALE))
                .roles(Set.of(RoleType.ADMINISTRATOR))
                .build();
        user2 = UserEntity.builder()
                .email("email2")
                .firstName("firstName2")
                .lastName("lastName2")
                .password("password2")
                .isTermsAndConditionsAccepted(false)
                .genders(Set.of(UserGender.FEMALE))
                .roles(Set.of(RoleType.USER))
                .build();

        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());

        userDto1 = new User();
        userDto1.setEmail("email1");
        userDto1.setFirstName("firstName1");
        userDto1.setLastName("lastName1");

        userDto2 = new User();
        userDto2.setEmail("email2");
        userDto2.setFirstName("firstName2");
        userDto2.setLastName("lastName2");

    }


    @Test
    public void shouldReturnUsersList(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.toDto(anyList())).thenReturn(Arrays.asList(userDto1, userDto2));

        ResponseEntity<List<User>> responseEntity = userService.getUsers();

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        List<User> userDTOs = responseEntity.getBody();
        assertNotNull(userDTOs);
        assertEquals(2, userDTOs.size());
        assertEquals(userDto1, userDTOs.get(0));
        assertEquals(userDto2, userDTOs.get(1));

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDto(anyList());
    }

    @Test
    public void shouldReturnUserById(){
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        ResponseEntity<User> responseEntity = userService.getUser(user1.getId().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        User userDTO = responseEntity.getBody();
        assertNotNull(userDTO);
        assertEquals(userDto1, userDTO);

        verify(userRepository, times(1)).findById(user1.getId());
        verify(userMapper, times(1)).toDto(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenUserNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<User> responseEntity = userService.getUser(user1.getId().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(404), responseEntity.getStatusCode());
    }

    @Test
    public void shouldUpdateUser() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        when(userMapper.toDto(user1)).thenReturn(userDto1);

        User updatedUser = new User();

        updatedUser.setEmail("newEmail");
        updatedUser.setFirstName("newFirstName");
        updatedUser.setLastName("newLastName");

        ResponseEntity<User> responseEntity = userService.updateUser(user1.getId().toString(), updatedUser);

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        User userDTO = responseEntity.getBody();
        assertNotNull(userDTO);
        assertEquals(userDto1, userDTO);
    }

    @Test
    public void shouldReturnNotFoundWhenUserToUpdateNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<User> responseEntity = userService.updateUser(user1.getId().toString(), new User());

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(404), responseEntity.getStatusCode());
    }

    @Test
    public void shouldDeleteUser() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        ResponseEntity<Void> responseEntity = userService.deleteUser(user1.getId().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenUserToDeleteNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<Void> responseEntity = userService.deleteUser(user1.getId().toString());

        assertNotNull(responseEntity);
        assertEquals(HttpStatusCode.valueOf(404), responseEntity.getStatusCode());
    }
}
