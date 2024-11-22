package com.trial.model;

import java.util.List;

import lombok.Data;

@Data
public class Page {
    private String title;
    private String tagLine;
    private String color;
    private String imageUrl;
    private List<Card> cards;
}

