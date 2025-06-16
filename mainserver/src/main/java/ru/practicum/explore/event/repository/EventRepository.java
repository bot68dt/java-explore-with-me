package ru.practicum.explore.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.event.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    Optional<Event> findByCategoryId(long catId);

    List<Event> findByInitiatorId(long userId, Pageable pageable);

    Optional<Event> findByIdAndState(long eventId, String state);

    @Query("SELECT e FROM Event e WHERE ((:paid = false or (:paid = true and e.paid = :paid)) and (e.eventDate >= :start) and (e.eventDate <= :end) and (:state1 is null or e.state in (:state1)) and (:text1 is null or e.annotation in (:text1) or e.description in (:text2) or :text2 is null) and (:catId is null or e.category.id in (:catId)) and e.participantLimit is not null)")
    Collection<Event> findEvents(@Param("paid") boolean paid, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("state1") List<String> state1, @Param("text1") List<String> text1, @Param("text2") List<String> text2, @Param("catId") List<Long> catId, PageRequest pageRequest);

    @Query("SELECT e FROM Event e WHERE ((:paid = false or (:paid = true and e.paid = :paid)) and (e.eventDate >= :start) and (e.eventDate <= :end) and (:state1 is null or e.state in (:state1)) and (:text1 is null or e.annotation in (:text1) or e.description in (:text2) or :text2 is null) and (:catId is null or e.category.id in (:catId)))")
    Collection<Event> findEventsNull(@Param("paid") boolean paid, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("state1") List<String> state1, @Param("text1") List<String> text1, @Param("text2") List<String> text2, @Param("catId") List<Long> catId, PageRequest pageRequest);

    @Query("SELECT e FROM Event e WHERE ((:users is null or e.initiator.id in (:users)) and (:states is null or UPPER(e.state) in (:states)) and (:categories is null or e.category.id in (:categories)) and (e.eventDate >= :start) and (e.eventDate <= :end))")
    Collection<Event> findEventsByAdm(@Param("users") List<Long> users, @Param("states") List<String> states, @Param("categories") List<Long> categories, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, PageRequest pageRequest);
}