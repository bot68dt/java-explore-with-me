/*package ru.practicum.shareit.item.controller;

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
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemTimingDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    void getItemsById() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemTimingDto itemTimingDto = new ItemTimingDto(1L, "name", "description", true, null, null, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.getItemsByUserId(listed.getId())).thenReturn(List.of(itemTimingDto));

        ResponseEntity<Collection<ItemTimingDto>> response = itemController.getItemsById(listed.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(itemTimingDto));
    }

    @Test
    void getItemById() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemTimingDto itemTimingDto = new ItemTimingDto(1L, "name", "description", true, null, null, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.getItemById(listed.getId(), itemTimingDto.getId())).thenReturn(itemTimingDto);

        ResponseEntity<ItemTimingDto> response = itemController.getItemById(listed.getId(), itemTimingDto.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), itemTimingDto);
    }

    @Test
    void searchItemByText() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemDto itemDto = new ItemDto(1L, "name", "description", true, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.searchItemByText(listed.getId(), "desc")).thenReturn(List.of(itemDto));

        ResponseEntity<Collection<ItemDto>> response = itemController.searchItemByText(listed.getId(), "desc");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(itemDto));
    }

    @Test
    void createItem() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemDto itemDto = new ItemDto(1L, "name", "description", true, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.createItem(listed.getId(), itemDto)).thenReturn(itemDto);

        ResponseEntity<ItemDto> response = itemController.createItem(listed.getId(), itemDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), itemDto);
    }

    @Test
    void update() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemDto itemDto = new ItemDto(1L, "name", "description", true, null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.updateItem(listed.getId(), itemDto, itemDto.getId())).thenReturn(itemDto);

        ResponseEntity<ItemDto> response = itemController.update(listed.getId(), itemDto, itemDto.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), itemDto);
    }

    @Test
    void comment() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemDto itemDto = new ItemDto(1L, "name", "description", true, null);
        CommentResponseDto commentResponseDto = new CommentResponseDto(1L, "string", "spring", LocalDateTime.now());
        CommentDto commentDto = new CommentDto("string");
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemService.comment(listed.getId(), commentDto, itemDto.getId())).thenReturn(commentResponseDto);

        ResponseEntity<CommentResponseDto> response = itemController.comment(listed.getId(), commentDto, itemDto.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), commentResponseDto);
    }
}*/