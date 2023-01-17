package com.emma.callapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pokemons(Long count, Results[] results) {

}
