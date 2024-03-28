package com.example.workouttracker.entityTests;

import com.example.workouttracker.mapper.UserMapper;
import com.example.workouttracker.user.RoleType;
import com.example.workouttracker.user.UserEntity;
import com.example.workouttracker.user.UserRepository;
import com.example.workouttracker.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserEntityTests {

    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Test
    public void whenCreateUserFails_thenThrowException() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Failed to save"));

        assertThrows(RuntimeException.class, () -> userService.createUser(user));

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void whenUserHasRole_thenGrantedAuthorityReflectsIt() {
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.USER);
        UserEntity user = UserEntity.builder()
                .username("user123")
                .email("user@example.com")
                .firstName("Maciej")
                .lastName("Wojturski")
                .password("securepassword")
                .roles(roles)
                .build();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority(RoleType.USER.name())));
    }

    @Test
    public void whenDeleteUser_thenUserIsDeleted() {
        // Przygotowanie danych testowych
        String userIdString = "1b274f79-ffa6-4d13-aedb-edc96139fc48";
        UUID userId = UUID.fromString(userIdString);
        UserEntity user = new UserEntity();
        user.setId(userId);

        // Konfiguracja zachowania mocków
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // Wywołanie testowanej metody
        ResponseEntity<Void> response = userService.deleteUser(userIdString);

        // Weryfikacja wyników
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }
}
