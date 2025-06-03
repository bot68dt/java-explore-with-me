package ru.practicum.main.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.user.dto.ChangedStatusOfRequestsDto;
import ru.practicum.main.user.dto.RequestDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapperNew;
import ru.practicum.main.user.model.Request;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.RequestRepository;
import ru.practicum.main.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public Collection<RequestDto> getUserRequests(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return UserMapperNew.mapToRequestDto(requestRepository.findByRequesterIdOrderByCreatedDateDesc(userId));
        else throw new EntityNotFoundException();
    }

    @Override
    public Collection<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        if (ids.equals(List.of(0L))) return UserMapperNew.mapToUserDto(userRepository.findAll(page));
        else {
            return new ArrayList<>(UserMapperNew.mapToUserDto(userRepository.findAllById(ids)));
        }
    }

    @Override
    @Transactional
    public RequestDto cancelRequest(long userId, long requestId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Request> request = requestRepository.findById(requestId);
            if (request.isPresent()) {
                Request canceledRequest = request.get();
                canceledRequest.setStatus("CANCELED");
                return UserMapperNew.mapToRequestDto(requestRepository.saveAndFlush(canceledRequest));
            } else throw new EntityNotFoundException();
        } else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) userRepository.deleteById(userId);
        else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public RequestDto createRequest(long userId, long eventId) {
        Request request = new Request();
        Optional<Event> event = eventRepository.findById(eventId);
        Optional<User> user = userRepository.findById(userId);
        Optional<Request> request1 = requestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (request1.isPresent() || (event.isPresent() && event.get().getInitiator().getId().equals(userId)) || (event.isPresent() && !event.get().getState().equals("PUBLISHED")) || (event.isPresent() && !event.get().getParticipantLimit().equals(0)))
            throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        if (event.isPresent() && event.get().getInitiator().getId() != userId && user.isPresent()) {
            request.setEventId(event.get().getId());
            request.setRequesterId(user.get().getId());
            return UserMapperNew.mapToRequestDto(requestRepository.saveAndFlush(request));
        } else throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.saveAndFlush(UserMapperNew.mapToUser(userDto));
        Optional<User> email = userRepository.findByEmail(userDto.getEmail());
        if (email.isPresent()) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        return UserMapperNew.mapToUserDto(user);
    }

    @Override
    public Collection<RequestDto> getEventRequests(long userId, long eventId) {
        return UserMapperNew.mapToRequestDto(requestRepository.findByEventId(eventId).get());
    }

    @Override
    @Transactional
    public Collection<RequestDto> changeRequestsStatuses(long userId, long eventId, ChangedStatusOfRequestsDto changedStatusOfRequestsDto) {
        Optional<Event> event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        if (event.isPresent()) {
            Optional<Collection<Request>> requests = requestRepository.findByEventId(eventId);
            if (requests.isPresent()) {
                Collection<RequestDto> result = new ArrayList<>();
                for (Long id : changedStatusOfRequestsDto.getRequestIds()) {
                    for (Request request : requests.get())
                        if (request.getId().equals(id)) {
                            if (event.get().getParticipantLimit().equals(0))
                                throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
                            request.setStatus(changedStatusOfRequestsDto.getStatus().toUpperCase());
                            Long l = event.get().getConfirmedRequests();
                            Event event1 = event.get();
                            event1.setConfirmedRequests(l + 1L);
                            eventRepository.saveAndFlush(event1);
                            result.add(UserMapperNew.mapToRequestDto(requestRepository.saveAndFlush(request)));
                        }
                }
                return result;
            } else throw new EntityNotFoundException();
        } else throw new EntityNotFoundException();
    }
}