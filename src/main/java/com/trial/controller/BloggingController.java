package com.trial.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trial.model.Blog;


@Controller
public class BloggingController {
    Blog blog;

    @PostMapping("/blogResponse")
    public String response(@ModelAttribute Blog b,Model model){
        blog=b;
        blog.setTime(LocalDateTime.now());
        model.addAttribute("blog", blog);
        System.out.println(model);
        System.out.println(blog);
        return "blogging";
    }
}
