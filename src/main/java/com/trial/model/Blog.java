package com.trial.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Blog {
    private String name;
    private String profession;
    private String title1;
    private String title2;
    private String title3;
    private String content;
    private String imageUrl;
    private LocalDateTime time;
}
