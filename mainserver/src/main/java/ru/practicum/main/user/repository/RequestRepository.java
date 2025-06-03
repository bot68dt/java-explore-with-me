package ru.practicum.main.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.main.user.model.Request;

import java.util.Collection;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> findByRequesterIdOrderByCreatedDateDesc(long userId);

    Optional<Request> findByRequesterIdAndEventId(long userId, long eventId);

    Optional<Collection<Request>> findByEventId(long eventId);
}