package com.emma.callapi.web;


import com.emma.callapi.entities.Pokemons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CallApiController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello World";
    }

    @GetMapping("/callHelloMapping")
    public String CallHelloUrl() {
        String uri = "http://localhost:8080/hello";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    @GetMapping("/callExternalPokemonApi")
    public Pokemons callExternalPokemonApi() {
        String uri = "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0";
        RestTemplate restTemplate = new RestTemplate();
        Pokemons pokemons = restTemplate.getForObject(uri, Pokemons.class);
        Long total = pokemons.count();
        System.out.println(total);
        List<String> collect = Arrays.stream(pokemons.results()).map(results -> results.name())
                .collect(Collectors.toList());
        System.out.println(collect);

        return pokemons;
    }


}
