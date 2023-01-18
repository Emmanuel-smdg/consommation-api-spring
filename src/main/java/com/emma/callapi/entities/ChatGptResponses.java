package com.emma.callapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGptResponses {
/*
    //We only keep the fields we want.
    public String id;
    public String object;
    public int created;
    public String model;*/
    public ArrayList<Choice> choices;
    //public Usage usage;
}

