package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    Optional<Event> findByCategoryId(long catId);

    List<Event> findByInitiatorId(long userId, Pageable pageable);

    Optional<Event> findByIdAndPublishedOnLessThanEqual(long eventId, LocalDateTime time);

    List<Event> findByPaidAndEventDateGreaterThanEqualAndEventDateLessThanEqualAndParticipantLimitGreaterThanEqual(boolean paid, LocalDateTime start, LocalDateTime end, Long limit, Pageable pageable);

    List<Event> findByPaidAndEventDateGreaterThanEqualAndEventDateLessThanEqualAndParticipantLimit(boolean paid, LocalDateTime start, LocalDateTime end, Long limit, Pageable pageable);

    List<Event> findByEventDateGreaterThanEqualAndEventDateLessThanEqual(LocalDateTime start, LocalDateTime end, Pageable pageable);
}