package ru.practicum.explore.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.client.BaseClient;
import ru.practicum.explore.user.dto.UserDto;

import java.util.Map;

@Service
public class AdminUserClient extends BaseClient {
    private static final String API_PREFIX = "/admin/users";

    @Autowired
    public AdminUserClient(@Value("${main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX)).requestFactory(() -> new HttpComponentsClientHttpRequestFactory()).build());
    }

    public ResponseEntity<Object> getAllUsers(Long ids, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("ids", ids, "from", from, "size", size);
        return get("?ids={ids}&from={from}&size={size}", null, parameters);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        Map<String, Object> parameters = Map.of("userId", userId);
        return delete("/{userId}", null, parameters);
    }

    public ResponseEntity<Object> createUser(UserDto userDto) {
        String name = userDto.getName();
        String email = userDto.getEmail();
        Map<String, Object> parameters = Map.of("name", name, "email", email);
        return post("?name={name}&email={email}", null, parameters, null);
    }
}