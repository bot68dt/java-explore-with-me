/*package ru.practicum.shareit.user.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void addUser() {
        userRepository.deleteAll();
    }

    /*Test
    void existsUserById() {
        User user = new User();
        user.setName("name");
        user.setEmail("ddd@gmail.com");
        user = userRepository.save(user);
        Assertions.assertTrue(userRepository.existsUserById(1L));
        userRepository.deleteAll();
    }*/

    /*@Test
    void findById() {
        User user = new User();
        user.setName("name");
        user.setEmail("ddd@gmail.com");
        user = userRepository.save(user);
        Assertions.assertTrue(userRepository.findById(1L).isPresent());
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setName("name");
        user.setEmail("ddd@gmail.com");
        user = userRepository.save(user);
        Assertions.assertTrue(userRepository.findByEmail("ddd@gmail.com").isPresent());
        userRepository.deleteAll();
    }

    @AfterEach
    public void delUser() {
        userRepository.deleteAll();
    }
}*/