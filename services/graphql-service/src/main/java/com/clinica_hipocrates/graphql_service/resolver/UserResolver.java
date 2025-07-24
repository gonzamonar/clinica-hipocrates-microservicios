package com.clinica_hipocrates.graphql_service.resolver;

import com.clinica_hipocrates.common.exception.ResourceNotFoundException;
import com.clinica_hipocrates.graphql_service.dto.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class UserResolver {

    private final RestTemplate restTemplate;
//    private final String userServiceBaseUrl = "http://user-service";
    private final String userServiceBaseUrl = "http://localhost:8082";

    public UserResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @QueryMapping
    public List<User> users(){
        String url = userServiceBaseUrl + "/users";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @QueryMapping
    public User user(@Argument Long id){
        try {
            String url = userServiceBaseUrl + "/users/" + id;
            return restTemplate.getForObject(url, User.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("El usuario con id " + id + " no existe.");
        }
    }
}
