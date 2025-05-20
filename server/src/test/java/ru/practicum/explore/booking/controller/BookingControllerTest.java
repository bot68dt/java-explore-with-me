/*package ru.practicum.shareit.booking.controller;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void createBooking() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        Item item = new Item();
        item.setName("name");
        item.setOwner(listed);
        item.setId(1L);
        item.setAvailable(true);
        item.setDescription("description");
        BookingRequest bookingRequest = new BookingRequest(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = new BookingDto(1L, bookingRequest.getStart(), bookingRequest.getEnd(), item, listed, Status.APPROVED);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(bookingService.createBooking(listed.getId(), bookingRequest)).thenReturn(bookingDto);

        ResponseEntity<BookingDto> response = bookingController.createBooking(listed.getId(), bookingRequest);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), bookingDto);
    }

    @Test
    void update() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        Item item = new Item();
        item.setName("name");
        item.setOwner(listed);
        item.setId(1L);
        item.setAvailable(true);
        item.setDescription("description");
        BookingRequest bookingRequest = new BookingRequest(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = new BookingDto(1L, bookingRequest.getStart(), bookingRequest.getEnd(), item, listed, Status.APPROVED);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(bookingService.updateStatus(listed.getId(), bookingDto.getId(), true)).thenReturn(bookingDto);

        ResponseEntity<BookingDto> response = bookingController.update(listed.getId(), bookingDto.getId(), true);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), bookingDto);
    }

    @Test
    void getBookingByUserId() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        Item item = new Item();
        item.setName("name");
        item.setOwner(listed);
        item.setId(1L);
        item.setAvailable(true);
        item.setDescription("description");
        BookingRequest bookingRequest = new BookingRequest(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = new BookingDto(1L, bookingRequest.getStart(), bookingRequest.getEnd(), item, listed, Status.APPROVED);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(bookingService.getBookingById(listed.getId(), bookingDto.getId())).thenReturn(bookingDto);

        ResponseEntity<BookingDto> response = bookingController.getBookingByUserId(listed.getId(), bookingDto.getId());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), bookingDto);
    }

    @Test
    void getAllBookingsByUserId() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        Item item = new Item();
        item.setName("name");
        item.setOwner(listed);
        item.setId(1L);
        item.setAvailable(true);
        item.setDescription("description");
        BookingRequest bookingRequest = new BookingRequest(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = new BookingDto(1L, bookingRequest.getStart(), bookingRequest.getEnd(), item, listed, Status.APPROVED);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(bookingService.getBookingsByUserId(listed.getId(), "ALL")).thenReturn(List.of(bookingDto));

        ResponseEntity<Collection<BookingDto>> response = bookingController.getAllBookingsByUserId(listed.getId(), "ALL");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(bookingDto));
    }

    @Test
    void getAllBookingsByOwnerId() {
        User listed = new User();
        listed.setName("spring");
        listed.setId(1L);
        listed.setEmail("ddd@gmail.com");
        Item item = new Item();
        item.setName("name");
        item.setOwner(listed);
        item.setId(1L);
        item.setAvailable(true);
        item.setDescription("description");
        BookingRequest bookingRequest = new BookingRequest(1L, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BookingDto bookingDto = new BookingDto(1L, bookingRequest.getStart(), bookingRequest.getEnd(), item, listed, Status.APPROVED);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Mockito.when(bookingService.getBookingsByOwnerId(listed.getId(), "ALL")).thenReturn(List.of(bookingDto));

        ResponseEntity<Collection<BookingDto>> response = bookingController.getAllBookingsByOwnerId(listed.getId(), "ALL");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(response.getBody(), List.of(bookingDto));
    }
}*/