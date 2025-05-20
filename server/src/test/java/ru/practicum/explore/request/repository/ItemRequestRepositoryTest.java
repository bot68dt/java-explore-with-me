/*package ru.practicum.shareit.request.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

@DataJpaTest
class ItemRequestRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRequestRepository itemRequestRepository;

    @BeforeEach
    public void delStart() {
        userRepository.deleteById(1L);
        userRepository.deleteById(2L);
        itemRequestRepository.deleteById(1L);
        itemRequestRepository.deleteById(2L);
    }

    @Test
    void findByRequesterIdOrderByCreatedDesc() {
        User user = new User();
        user.setName("name");
        user.setEmail("ddd@gmail.com");
        user = userRepository.save(user);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription("description");
        itemRequestRepository.save(itemRequest);
        Assertions.assertFalse(itemRequestRepository.findByRequesterIdOrderByCreatedDesc(3L).isEmpty());
    }

    @Test
    void findByRequesterIdNotOrderByCreatedDesc() {
        User user = new User();
        user.setName("name");
        user.setEmail("ddd@gmail.com");
        user = userRepository.save(user);
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setDescription("description");
        itemRequestRepository.save(itemRequest);
        User user1 = new User();
        user1.setName("name1");
        user1.setEmail("ddd1@gmail.com");
        user1 = userRepository.save(user1);
        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setRequester(user1);
        itemRequest1.setCreated(LocalDateTime.now());
        itemRequest1.setDescription("description1");
        itemRequestRepository.save(itemRequest1);
        Assertions.assertFalse(itemRequestRepository.findByRequesterIdOrderByCreatedDesc(2L).isEmpty());
    }

    @AfterEach
    public void del() {
        userRepository.deleteById(1L);
        userRepository.deleteById(2L);
        itemRequestRepository.deleteById(1L);
        itemRequestRepository.deleteById(2L);
    }
}*/