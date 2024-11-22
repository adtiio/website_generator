package com.trial.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatBotService {
    String GEMINI_API_KEY="AIzaSyDwPa9c4zRh0TdEQvzhIts9nrlvLlffZX8";

   
    public String simpleChat(String prompt) {
        String GEMINI_API_URL="https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key="
                +GEMINI_API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = new JSONObject()
                .put("contents", new JSONArray()
                        .put(new JSONObject()
                                .put("parts",new JSONArray()
                                        .put(new JSONObject()
                                                .put("text",prompt))))
                ).toString();

        HttpEntity<String> requestEntity=new HttpEntity<>(requestBody,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=restTemplate.postForEntity(GEMINI_API_URL,requestEntity,String.class);

        return response.getBody();
    }
}
