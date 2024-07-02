package com.example.workouttracker.user.service;

import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.core.user.*;
import com.example.workouttracker.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.openapitools.model.UserCreate;
import org.openapitools.model.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

    private UserEntity user1;
    private UserEntity user2;
    private UserDetails userDetails1;
    private UserDetails userDetails2;
    private UserCreate userCreate;

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

        userDetails1 = new UserDetails();
        userDetails1.setEmail("email1");
        userDetails1.setFirstName("firstName1");
        userDetails1.setLastName("lastName1");

        userDetails2 = new UserDetails();
        userDetails2.setEmail("email2");
        userDetails2.setFirstName("firstName2");
        userDetails2.setLastName("lastName2");

        userCreate = new UserCreate();
        userCreate.setEmail("newEmail");
        userCreate.setFirstName("newFirstName");
        userCreate.setLastName("newLastName");
    }

    @Test
    public void shouldReturnUsersList() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(Arrays.asList(user1, user2)));
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDetails1).thenReturn(userDetails2);

        List<User> users = userService.getUsers(0, 10);

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(userDetails1, users.get(0));
        assertEquals(userDetails2, users.get(1));

        verify(userRepository, times(1)).findAll(any(PageRequest.class));
        verify(userMapper, times(2)).toDto(any(UserEntity.class));
    }

    @Test
    public void shouldReturnUserById() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDetailsDto(user1)).thenReturn(userDetails1);

        UserDetails userDetails = userService.getUser(user1.getId().toString());

        assertNotNull(userDetails);
        assertEquals(userDetails1, userDetails);

        verify(userRepository, times(1)).findById(user1.getId());
        verify(userMapper, times(1)).toDetailsDto(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenUserNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(UserException.class, () -> {
            userService.getUser(user1.getId().toString());
        });

        verify(userRepository, times(1)).findById(user1.getId());
    }

    @Test
    public void shouldReturnCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getEmail());

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDetails1);

        User user = userService.getCurrentUser();

        assertNotNull(user);
        assertEquals(userDetails1, user);

        verify(userRepository, times(1)).findByEmail(user1.getEmail());
        verify(userMapper, times(1)).toDto(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenCurrentUserNotFound() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getEmail());

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.empty());

        assertThrows(UserException.class, () -> {
            userService.getCurrentUser();
        });

        verify(userRepository, times(1)).findByEmail(user1.getEmail());
    }

    @Test
    public void shouldUpdateCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getEmail());

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.findByEmail(userCreate.getEmail())).thenReturn(java.util.Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user1);
        when(userMapper.toDto(user1)).thenReturn(userDetails1);

        User user = userService.updateCurrentUser(userCreate);

        assertNotNull(user);
        assertEquals(userDetails1, user);

        verify(userRepository, times(1)).findByEmail(user1.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).toDto(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenCurrentUserToUpdateNotFound() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getEmail());

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.empty());

        assertThrows(UserException.class, () -> userService.updateCurrentUser(userCreate));

        verify(userRepository, times(1)).findByEmail(user1.getEmail());
    }

    @Test
    public void shouldReturnNotUniqueEmailWhenUpdatingCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user1.getEmail());

        when(userRepository.findByEmail(user1.getEmail())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.findByEmail(userCreate.getEmail())).thenReturn(java.util.Optional.of(user2));

        assertThrows(UserException.class, () -> userService.updateCurrentUser(userCreate));

        verify(userRepository, times(1)).findByEmail(user1.getEmail());
        verify(userRepository, times(1)).findByEmail(userCreate.getEmail());
    }

    @Test
    public void shouldUpdateUser() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user1);
        when(userMapper.toDetailsDto(user1)).thenReturn(userDetails1);

        UserDetails userDetails = userService.updateUser(user1.getId().toString(), userCreate);

        assertNotNull(userDetails);
        assertEquals(userDetails1, userDetails);

        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).toDetailsDto(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenUserToUpdateNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(UserException.class, () -> userService.updateUser(user1.getId().toString(), userCreate));

        verify(userRepository, times(1)).findById(user1.getId());
    }

    @Test
    public void shouldDeleteUser() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        userService.deleteUser(user1.getId().toString());

        verify(userRepository, times(1)).findById(user1.getId());
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    public void shouldReturnNotFoundWhenUserToDeleteNotFound() {
        when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(UserException.class, () -> userService.deleteUser(user1.getId().toString()));

        verify(userRepository, times(1)).findById(user1.getId());
    }
}