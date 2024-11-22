package com.trial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String brand;
    private String description;
    private String image;
    private String color;
    private String price;
    private String discountedPrice;
    private String percentage;
    private String type;

    public Card(Card card){
            this.brand=card.getBrand();
            this.description=card.getDescription();
            this.image=card.getImage();
            this.color=card.getColor();
            this.price=card.getPrice();
            this.discountedPrice=card.getDiscountedPrice();
            this.percentage=card.getPercentage();
            this.type=card.getType();
    }
}




// title
// descrpition
// price
// discounted price
// percentage off
// type


