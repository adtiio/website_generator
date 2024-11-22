package com.trial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trial.service.ChatBotService;

@RestController
@RequestMapping("/ai/chat")
public class ChatBotController {

    @Autowired
    ChatBotService chatBotService;

    @PostMapping("/simple")
    public ResponseEntity<String> simpleChatHandler(@RequestBody String prompt) throws Exception {
        String response = chatBotService.simpleChat(prompt);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
