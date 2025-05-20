/*package ru.practicum.shareit.request.controller;

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
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private ItemRequestService itemRequestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    @Test
    void createItemRequest() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemRequestDto itemRequestDto = new ItemRequestDto("boot");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(listed);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setId(1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemRequestService.createRequest(1L, itemRequestDto)).thenReturn(itemRequest);

        ResponseEntity<ItemRequest> response = itemRequestController.createItemRequest(listed.getId(), itemRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), itemRequest);
    }

    @Test
    void getItemRequestByRequestId() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemRequestDto itemRequestDto = new ItemRequestDto("boot");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(listed);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setId(1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemRequestService.getItemRequestById(1L, itemRequest.getId())).thenReturn(itemRequest);

        ResponseEntity<ItemRequest> response = itemRequestController.getItemRequestByRequestId(listed.getId(), itemRequest.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), itemRequest);
    }

    @Test
    void getAllItemRequestsByUserId() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        ItemRequestDto itemRequestDto = new ItemRequestDto("boot");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(listed);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setId(1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemRequestService.getItemRequestsByUserId(listed.getId())).thenReturn(List.of(itemRequest));

        ResponseEntity<Collection<ItemRequest>> response = itemRequestController.getAllItemRequestsByUserId(listed.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(itemRequest));
    }

    @Test
    void getAllItemRequestsByOthers() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        User listed2 = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd2@gmail.com");
        ItemRequestDto itemRequestDto = new ItemRequestDto("boot");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(listed2);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setId(1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(itemRequestService.getItemRequestsByOthers(listed.getId())).thenReturn(List.of(itemRequest));

        ResponseEntity<Collection<ItemRequest>> response = itemRequestController.getAllItemRequestsByOthers(listed.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(itemRequest));
    }
}*/