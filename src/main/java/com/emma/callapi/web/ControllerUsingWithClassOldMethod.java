package com.emma.callapi.web;

import com.emma.callapi.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class ControllerUsingWithClassOldMethod {
    @GetMapping("/callExternalJsonPlaceHolderApi")
    public Users[] getAllUsers() {
        String uri = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        Users[] users = restTemplate.getForObject(uri, Users[].class);
        return users;
    }

    @PostMapping("/callExternalJsonPlaceHolderApi")
    public Users createNewUser(@RequestBody Users user) {
        String uri = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Users> userResponseEntity = restTemplate.postForEntity(uri, user, Users.class);
        return userResponseEntity.getBody();
    }

    @PutMapping("/callExternalJsonPlaceHolderApi/{id}")
    public Users updateUser(@PathVariable(name = "id") int id) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id;
        Users user = Arrays.stream(getAllUsers()).filter(users -> users.id == id).findFirst().get();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, user);
        return user;
    }

    @DeleteMapping("/callExternalJsonPlaceHolderApi/{id}")
    public void deleteUser(@PathVariable(name = "id") int id) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri);
    }

}
