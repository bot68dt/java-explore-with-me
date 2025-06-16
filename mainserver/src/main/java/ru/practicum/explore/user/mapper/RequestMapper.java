package ru.practicum.explore.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explore.user.dto.RequestDto;
import ru.practicum.explore.user.model.Request;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static List<RequestDto> mapToRequestDto(Iterable<Request> requests) {
        List<RequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(mapToRequestDto(request));
        }
        return result;
    }

    public static RequestDto mapToRequestDto(Request changeable) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(changeable.getId());
        requestDto.setRequester(changeable.getRequesterId());
        requestDto.setEvent(changeable.getEventId());
        requestDto.setStatus(changeable.getStatus());
        requestDto.setCreated(changeable.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return requestDto;
    }
}