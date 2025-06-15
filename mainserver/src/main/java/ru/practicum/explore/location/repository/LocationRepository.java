package ru.practicum.explore.location.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explore.location.model.Location;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Collection<Location> findByLatInAndLonInAndPlaceIgnoreCaseInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> place, List<String> street, List<String> town, PageRequest pageRequest);

    Collection<Location> findByPlaceIgnoreCaseInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(List<String> place, List<String> street, List<String> town, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndStreetIgnoreCaseInAndTownIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> street, List<String> town, PageRequest pageRequest);

    Collection<Location> findByStreetIgnoreCaseInAndTownIgnoreCaseIn(List<String> street, List<String> town, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndPlaceIgnoreCaseInAndTownIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> place, List<String> town, PageRequest pageRequest);

    Collection<Location> findByPlaceIgnoreCaseInAndTownIgnoreCaseIn(List<String> place, List<String> town, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndTownIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> town, PageRequest pageRequest);

    Collection<Location> findByTownIgnoreCaseIn(List<String> town, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndPlaceIgnoreCaseInAndStreetIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> place, List<String> street, PageRequest pageRequest);

    Collection<Location> findByPlaceIgnoreCaseInAndStreetIgnoreCaseIn(List<String> place, List<String> street, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndStreetIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> street, PageRequest pageRequest);

    Collection<Location> findByStreetIgnoreCaseIn(List<String> street, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonInAndPlaceIgnoreCaseIn(Set<Float> lat, Collection<Float> lon, List<String> place, PageRequest pageRequest);

    Collection<Location> findByPlaceIgnoreCaseIn(List<String> place, PageRequest pageRequest);

    Collection<Location> findByLatInAndLonIn(Set<Float> lat, Collection<Float> lon, PageRequest pageRequest);

    @Query(value = " select a.town, a.street, a.place, a.lat, a.lon, distance(a.lat, a.lon, ?1, ?2) from Location a where (distance(a.lat, a.lon, ?1, ?2) <= ?3) ")
    List<Object[]> findAllInRadius(float lat, float lon, float radius, PageRequest pageRequest);
}