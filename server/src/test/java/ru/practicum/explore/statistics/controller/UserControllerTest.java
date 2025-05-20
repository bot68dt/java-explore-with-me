/*package ru.practicum.shareit.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUserById() {
        UserDto expected = new UserDto();
        Mockito.when(userService.getUserById(0L)).thenReturn(expected);

        ResponseEntity<UserDto> response = userController.getUserById(0L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), expected);
    }

    @Test
    void createUser() {
        UserDto listed = new UserDto(1L, "ddd@gmail.com", "spring");
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(userService.addUser(listed)).thenReturn(listed);

        ResponseEntity<UserDto> response = userController.createUser(listed);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), listed);
    }

    @Test
    void update() {
        UserDto expected = new UserDto(1L, "ddd1@gmail.com", "spring");
        Mockito.when(userService.updateUser(1L, expected)).thenReturn(expected);

        ResponseEntity<UserDto> response = userController.update(1L, expected);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), expected);
    }

    @Test
    void deleteUser() {
        UserDto expected = new UserDto(1L, "ddd1@gmail.com", "spring");
        Mockito.doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertFalse(response.hasBody());
    }
}*/