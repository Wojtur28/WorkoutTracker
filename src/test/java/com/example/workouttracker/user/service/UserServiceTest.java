package com.example.workouttracker.user.service;

import com.example.model.User;
import com.example.model.UserCreate;
import com.example.model.UserDetails;
import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.core.user.*;
import com.example.workouttracker.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity1;
    private UserEntity userEntity2;
    private User userDto1;
    private User userDto2;
    private UserDetails userDetailsDto1;
    private UserCreate userCreate;

    @BeforeEach
    public void setUp() {
        userEntity1 = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .age(52)
                .height(180)
                .build();

        userEntity2 = UserEntity.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .age(34)
                .height(190)
                .build();

        userEntity1.setId(UUID.randomUUID());
        userEntity2.setId(UUID.randomUUID());

        userDto1 = new User();
        userDto1.setId(userEntity1.getId().toString());
        userDto1.setFirstName(userEntity1.getFirstName());
        userDto1.setLastName(userEntity1.getLastName());
        userDto1.setEmail(userEntity1.getEmail());
        userDto1.setAge(userEntity1.getAge());
        userDto1.setHeight(userEntity1.getHeight());

        userDto2 = new User();
        userDto2.setId(userEntity2.getId().toString());
        userDto2.setFirstName(userEntity2.getFirstName());
        userDto2.setLastName(userEntity2.getLastName());
        userDto2.setEmail(userEntity2.getEmail());
        userDto2.setAge(userEntity2.getAge());
        userDto2.setHeight(userEntity2.getHeight());

        userDetailsDto1 = new UserDetails();
        userDetailsDto1.setId(userEntity1.getId().toString());
        userDetailsDto1.setFirstName(userEntity1.getFirstName());
        userDetailsDto1.setLastName(userEntity1.getLastName());
        userDetailsDto1.setEmail(userEntity1.getEmail());
        userDetailsDto1.setAge(userEntity1.getAge());
        userDetailsDto1.setHeight(userEntity1.getHeight());

        userCreate = new UserCreate();
        userCreate.setFirstName("UpdatedFirstName");
        userCreate.setLastName("UpdatedLastName");
        userCreate.setEmail("updated.email@example.com");
        userCreate.setGender("MALE");
        userCreate.setAge(32);
        userCreate.setHeight(177);
    }

    @Test
    public void shouldGetUsersSuccessfully() {
        Page<UserEntity> pageResult = new PageImpl<>(List.of(userEntity1, userEntity2));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(pageResult);
        when(userMapper.toDto(userEntity1)).thenReturn(userDto1);
        when(userMapper.toDto(userEntity2)).thenReturn(userDto2);

        List<User> result = userService.getUsers(0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userDto1, result.get(0));
        assertEquals(userDto2, result.get(1));
    }

    @Test
    public void shouldGetUserByIdSuccessfully() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity1));
        when(userMapper.toDetailsDto(userEntity1)).thenReturn(userDetailsDto1);

        UserDetails result = userService.getUser(userId.toString());

        assertNotNull(result);
        assertEquals(userDetailsDto1, result);
    }

    @Test
    public void shouldThrowNotFoundWhenGettingNonExistentUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () ->
                userService.getUser(userId.toString())
        );

        assertEquals(UserException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }

    @Test
    public void shouldGetCurrentUserSuccessfully() {
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity1));
            when(userMapper.toDto(userEntity1)).thenReturn(userDto1);

            User result = userService.getCurrentUser();

            assertNotNull(result);
            assertEquals(userDto1, result);
        }
    }

    @Test
    public void shouldThrowNotFoundWhenGettingCurrentUser() {
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

            UserException exception = assertThrows(UserException.class, () ->
                    userService.getCurrentUser()
            );

            assertEquals(UserException.FailReason.NOT_FOUND, exception.getFailReason());
            assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
        }
    }


    @Test
    public void shouldUpdateCurrentUserSuccessfully() {
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity1));
            when(userMapper.toDto(userEntity1)).thenReturn(userDto1);

            User result = userService.updateCurrentUser(userCreate);

            assertNotNull(result);
            verify(userRepository).save(userEntity1);
            assertEquals(userDto1, result);
        }
    }


    @Test
    public void shouldThrowNotFoundWhenUpdatingCurrentUser() {
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("test@example.com");

            when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

            UserException exception = assertThrows(UserException.class, () ->
                    userService.updateCurrentUser(userCreate)
            );

            assertEquals(UserException.FailReason.NOT_FOUND, exception.getFailReason());
            assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
        }
    }


    @Test
    public void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity1));
        when(userMapper.toDetailsDto(userEntity1)).thenReturn(userDetailsDto1);

        UserDetails result = userService.updateUser(userId.toString(), userCreate);

        assertNotNull(result);
        verify(userRepository).save(userEntity1);
        assertEquals(userDetailsDto1, result);
    }

    @Test
    public void shouldThrowNotFoundWhenUpdatingNonExistentUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () ->
                userService.updateUser(userId.toString(), userCreate)
        );

        assertEquals(UserException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }

    @Test
    public void shouldDeleteUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity1));

        userService.deleteUser(userId.toString());

        verify(userRepository).delete(userEntity1);
    }

    @Test
    public void shouldThrowNotFoundWhenDeletingNonExistentUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () ->
                userService.deleteUser(userId.toString())
        );

        assertEquals(UserException.FailReason.NOT_FOUND, exception.getFailReason());
        assertEquals("Error occurred: NOT_FOUND", exception.getMessage());
    }
}
