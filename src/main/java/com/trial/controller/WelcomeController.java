package com.trial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trial.model.Req;


@Controller
public class WelcomeController {

    @GetMapping("/")
    public String input() {
        return "welcome";
    }

    @PostMapping("/welcome")
    public String websiteType(@ModelAttribute Req request){
        System.out.println(request.getWebType());
        if(request.getWebType().equals("ecommerce")) return "inputprompt";
        else if(request.getWebType().equals("blogging")) return "bloginput";
        else if(request.getWebType().equals("todo-list")) return "todolist";
        else if(request.getWebType().equals("ai-chatbot")) return "chatbot";
        else if(request.getWebType().equals("video-streaming")) return "redirect:http://localhost:5173";
        
 
        return "welcome";
    }


}
