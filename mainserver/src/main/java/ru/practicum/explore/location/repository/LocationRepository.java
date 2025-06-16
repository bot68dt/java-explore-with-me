package ru.practicum.explore.location.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.location.model.Location;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE ((:lat is null or l.lat in (:lat)) and (:lon is null or l.lon in (:lon)) and (:place is null or l.place in (:place)) and (:street is null or l.street in (:street)) and (:town is null or l.town in (:town)))")
    Collection<Location> findLocationByParams(@Param("lat") Set<Float> lat, @Param("lon") Collection<Float> lon, @Param("place") List<String> place, @Param("street") List<String> street, @Param("town") List<String> town, PageRequest pageRequest);

    @Query(value = " select a.town, a.street, a.place, a.lat, a.lon, distance(a.lat, a.lon, :lat, :lon) from Location a where (distance(a.lat, a.lon, :lat, :lon) <= :radius) ")
    List<Object[]> findAllInRadius(@Param("lat") float lat, @Param("lon") float lon, @Param("radius") float radius, PageRequest pageRequest);
}