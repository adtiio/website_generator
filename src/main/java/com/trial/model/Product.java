package com.trial.model;


import java.util.List;

import lombok.Data;

@Data
public class Product {
    
    
    public String title;
    public String description;
    public String brand;
    public String color;
    public String imageUrl;
    public String backgroundColor;
    private String numberOfCards;
    public List<Card> cards;

   
}
