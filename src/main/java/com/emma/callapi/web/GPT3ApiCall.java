package com.emma.callapi.web;

import com.emma.callapi.entities.ChatGptResponses;
import com.emma.callapi.entities.DataInstruction;
import com.emma.callapi.utils.ApiKeys;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GPT3ApiCall {


    /**
     * Call of GPT-3 completion endpoint,
     *
     * @param prompt
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/chat")
    public ChatGptResponses completePrompt(@RequestBody String prompt) throws JsonProcessingException {
        return prepareCompletionRequest(prompt);
    }

    private ChatGptResponses prepareCompletionRequest(String prompt) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ApiKeys.OPENAI_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        //Prompt is a simple text. eg. In postman Post body property choose raw and text and write your text.

        data.put("prompt", prompt);
        data.put("model", "text-davinci-003");
        data.put("max_tokens", 2048); //Depending on your context tokens length

        String json = objectMapper.writeValueAsString(data);
        System.out.println(json);

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ChatGptResponses> response = restTemplate.postForEntity(
                ApiKeys.API_URL + "/completions", request, ChatGptResponses.class);

        return response.getBody();
    }


    @PostMapping("/inputAndInstruction")
    public ChatGptResponses giveInputAndInstruction(@RequestBody DataInstruction dataInstruction) throws JsonProcessingException {
        return prepareInputAndInstruction(dataInstruction);
    }

    private ChatGptResponses prepareInputAndInstruction(DataInstruction dataInstruction) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + ApiKeys.OPENAI_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();

        /**
         * Exemple
         * {
         *
         *     "input": "Les enfants mange",
         *     "instruction": "Corrige ces fautes d'orthographes"
         * }
         */

        data.put("input", dataInstruction.getInput());
        data.put("instruction", dataInstruction.getInstruction());
        data.put("model", "text-davinci-edit-001");


        String json = objectMapper.writeValueAsString(data);
        System.out.println(json);

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ChatGptResponses> response = restTemplate.postForEntity(
                ApiKeys.API_URL + "/edits", request, ChatGptResponses.class);

        return response.getBody();
    }
}
