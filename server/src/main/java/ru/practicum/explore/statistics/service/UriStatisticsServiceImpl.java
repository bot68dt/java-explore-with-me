package ru.practicum.explore.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.statistics.dto.HitStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDto;
import ru.practicum.explore.statistics.dto.UriStatisticsDtoWithHits;
import ru.practicum.explore.statistics.mapper.UriStatisticsMapperNew;
import ru.practicum.explore.statistics.model.HitsStatistics;
import ru.practicum.explore.statistics.model.Statistics;
import ru.practicum.explore.statistics.model.UniqueHitsStatistics;
import ru.practicum.explore.statistics.repository.HitStatisticsRepository;
import ru.practicum.explore.statistics.repository.UniqueHitStatisticsRepository;
import ru.practicum.explore.statistics.repository.UriStatisticsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UriStatisticsServiceImpl implements UriStatisticsService {

    private final UriStatisticsRepository uriStatisticsRepository;
    private final HitStatisticsRepository hitStatisticsRepository;
    private final UniqueHitStatisticsRepository uniqueHitStatisticsRepository;

    @Override
    public List<HitStatisticsDto> getUriStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<HitStatisticsDto> collection = new ArrayList<>();
        if (uris.isEmpty()) {
            uris = uriStatisticsRepository.findAllUris(start, end);
            if (unique) {
                for (String uri : uris) {
                    collection.add(UriStatisticsMapperNew.mapToUniqueHitStatistics(uniqueHitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByUniqueDesc(uri, start, end).get()));
                }
            } else {
                for (String uri : uris) {
                    collection.add(UriStatisticsMapperNew.mapToHitStatistics(hitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByHitsDesc(uri, start, end).get()));
                }
            }
            collection.sort(Comparator.comparing(HitStatisticsDto::getHits).reversed());
            return collection;
        }
        Optional<UniqueHitsStatistics> checkUniqueHits;
        Optional<HitsStatistics> chekHits;
        for (String uri : uris) {
            if (unique) {
                checkUniqueHits = uniqueHitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByUniqueDesc(uri, start, end);
                checkUniqueHits.ifPresent(uniqueHitsStatistics -> collection.add(UriStatisticsMapperNew.mapToUniqueHitStatistics(uniqueHitsStatistics)));
            } else {
                chekHits = hitStatisticsRepository.findFirst1ByUriAndTimeGreaterThanEqualAndTimeLessThanEqualOrderByHitsDesc(uri, start, end);
                chekHits.ifPresent(uniqueHitsStatistics -> collection.add(UriStatisticsMapperNew.mapToHitStatistics(uniqueHitsStatistics)));
            }
        }
        collection.sort(Comparator.comparing(HitStatisticsDto::getHits).reversed());
        return collection;
    }

    @Override
    @Transactional
    public UriStatisticsDtoWithHits addUriStatistics(UriStatisticsDto uriStatistics) {
        Optional<Statistics> statistics = uriStatisticsRepository.findFirst1ByUriAndAppAndIpOrderByHitsDesc(uriStatistics.getUri(), uriStatistics.getApp(), uriStatistics.getIp());
        UriStatisticsDtoWithHits stat;
        if (statistics.isEmpty()) {
            statistics = uriStatisticsRepository.findFirst1ByUriAndAppOrderByHitsDesc(uriStatistics.getUri(), uriStatistics.getApp());
            if (statistics.isEmpty())
                stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, 1L, 1L)));
            else
                stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, statistics.get().getHits() + 1L, statistics.get().getUnique() + 1L)));
        } else
            stat = UriStatisticsMapperNew.mapToNewUriStatisticsDtoWithHits(uriStatisticsRepository.saveAndFlush(UriStatisticsMapperNew.mapToNewUriStatistics(uriStatistics, statistics.get().getHits() + 1L, statistics.get().getUnique())));
        return (stat);
    }

    /*@Override
    public UserDto getUserById(long id) {
        Optional<UriStatistics> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("Getting user failed: user with ID {} not found", id);
            throw new UserNotFoundException("Error when getting user", id);
        }
        log.debug("Getting user with ID {}", id);
        return UserMapperNew.mapToUserDto(user.get());
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto newUserRequest) {

        if (newUserRequest.getName() == null) {
            log.debug("User had no name");
            throw new UserCreationException("Error when creating new user", "User has no name");
        }
        if (newUserRequest.getEmail() == null) {
            log.debug("User had no email");
            throw new UserCreationException("Error when creating new user", "User has no email");
        }
        if (userRepository.findByEmail(newUserRequest.getEmail()).isPresent()) {
            log.debug("User had duplicated email");
            throw new UserEmailException("Error when updating user", "User has duplicated email");
        }
        UriStatistics uriStatistics = userRepository.saveAndFlush(UserMapperNew.mapToNewUser(newUserRequest));
        log.debug("Adding new user {}", newUserRequest);
        return UserMapperNew.mapToUserDto(uriStatistics);
    }

    @Override
    @Transactional
    public UserDto updateUser(long id, UserDto updateUserRequest) {
        UriStatistics uriStatistics = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Updating user failed: user with ID {} not found", id);
            return new UserNotFoundException("Error when updating user", id);
        });

        if (userRepository.findByEmail(updateUserRequest.getEmail()).isPresent()) {
            log.debug("User had duplicated email");
            throw new UserEmailException("Error when updating user", "User has duplicated email");
        }
        log.debug("Updating user with ID {}: {}", id, updateUserRequest);
        UserMapperNew.updateUserFields(uriStatistics, updateUserRequest);
        userRepository.saveAndFlush(uriStatistics);
        return UserMapperNew.mapToUserDto(uriStatistics);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        if (!userRepository.existsUserById(userId)) {
            log.warn("Deleting user failed: user with ID {} not found", userId);
            throw new UserNotFoundException("Error when deleting user", userId);
        }
        log.debug("Deleting user with ID {}", userId);
        userRepository.deleteById(userId);
    }*/
}