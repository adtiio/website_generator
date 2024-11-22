package com.trial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trial.model.Card;
import com.trial.model.Edit;
import com.trial.model.Product;

@Controller
public class CheckController {

    Product prod;



    @PostMapping("/response")
    public String home(@RequestBody HashMap<String,String> prompt,Model model) {
            
            System.out.println(prompt.get("prompt"));
           // prod=product;
           Product product= parseProductDetails(prompt.get("prompt"));
           prod=product;
           String numberOfCards=product.getNumberOfCards();
           if(numberOfCards==null) numberOfCards="6";
           List<Card> cards = getAllProducts(Integer.parseInt(numberOfCards));

            prod.setCards(cards);
            prod.setBannerImg("https://images.all-free-download.com/images/thumbjpg/ecommerce_website_banner_template_customers_sketch_flat_design_6920122.jpg");
  
            model.addAttribute("products", prod);
            return "redirect:/response";
    }

    
    
    @PostMapping("/prompt/")
    public String getPrompt(@RequestBody HashMap<String,String> prompt){
        System.out.println(prompt.get("prompt"));
        Map<String,Object> map=parseCard(prompt.get("prompt"));
        Card card= (Card)map.get("card");
        String action=(String) map.get("action");
        String number=(String) map.get("number");
        String what=(String) map.get("what");
        Edit edit=(Edit) map.get("edit");

        if(action.equals("add")){    

                prod.getCards().add(card);  

        }else if(action.equals("update")){
            System.out.println(number);
            int num=Integer.parseInt(number)-1;
            Card updatedCard=new Card(prod.getCards().get(num));
            if(card.getBrand()!=null) updatedCard.setBrand(card.getBrand());
            if(card.getDescription()!=null) updatedCard.setDescription(card.getDescription());
            if(card.getImage()!=null) updatedCard.setImage(card.getImage());
            if(card.getColor()!=null) updatedCard.setColor(card.getColor());
            if(card.getPrice()!=null) updatedCard.setPrice(card.getPrice());
            if(card.getDiscountedPrice()!=null) updatedCard.setDiscountedPrice(card.getDiscountedPrice());
            if(card.getPercentage()!=null) updatedCard.setPercentage(card.getPercentage());


            prod.getCards().set(num, updatedCard);
        }else if(action.equals("remove")){
            System.out.println(number);
            int num=Integer.parseInt(number)-1;
            prod.getCards().remove(num);
        }else if(action.equals("change")){
            System.out.println(what);
            if(what==null){
                if(card.getBrand()!=null) prod.setTitle(card.getBrand());
                if(card.getDescription()!=null) prod.setDescription(card.getDescription());
            }
            else if(what!=null && what.equals("banner")){   
                prod.setBannerImg(card.getImage());
            }else if(what.equals("color")){
                prod.setColor(edit.getColor());
            }else if(what.equals("background color")){
                prod.setBackgroundColor(edit.getBackgroundColor());
            }
        }

        return "redirect:/response";
    }

    @GetMapping("/response")
    public String outputResponse(Model model) { 
        model.addAttribute("products", prod);
        return "check";
    }
    @PostMapping("/nextPage")
    public String viewProductDetails(@RequestParam("index") int index,Model model) {
    
        Card card = prod.getCards().get(index);
        model.addAttribute("card", card);
        return"details";
    }

    
    public static Map<String, Object> parseCard(String input) {
        Card card = new Card();
        Edit edit = new Edit(); 
        String action = null;
        String number=null;
        String what=null;

        Pattern actionPattern = Pattern.compile("^(add|update|remove|change)", Pattern.CASE_INSENSITIVE);
        Matcher actionMatcher = actionPattern.matcher(input);



        if (actionMatcher.find()) {
            action = actionMatcher.group(1).toLowerCase(); 
        }

        Pattern numberPattern = Pattern.compile("at index (\\d+)");
        Matcher numberMatcher = numberPattern.matcher(input);
        if(numberMatcher.find()){
            number=numberMatcher.group(1);
        }

        Pattern whatPattern=Pattern.compile("(background color|color|banner)[\\s:]*to?[\\s:]*([a-zA-Z]+)?");
        Matcher whatMatcher=whatPattern.matcher(input);
        if(whatMatcher.find()){
            what=whatMatcher.group(1);
            String color = whatMatcher.group(2);
            if(what.equals("background color")){
                edit.setBackgroundColor(color);
            }else if(what.equals("color")){
                edit.setColor(color);
            }
        }

        



        Pattern pattern = Pattern.compile("(\\w+):([^,]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();

            switch (key.toLowerCase()) {
                case "brand":
                    card.setBrand(value);
                    break;
                case "description":
                    card.setDescription(value);
                    break;
                case "image":
                    card.setImage(value);
                    break;
                case "color":
                    card.setColor(value);
                    break;
                case "price":
                    card.setPrice(value);
                    break;
                case "discountedprice":
                    card.setDiscountedPrice(value);
                    break;
                case "type":
                    card.setType(value);
                    break;
                default:
                    
                    break;
            }
        }

        if (card.getPrice() != null && card.getDiscountedPrice() != null) {
            try {
                double price = Double.parseDouble(card.getPrice());
                double discountedPrice = Double.parseDouble(card.getDiscountedPrice());
                double discountPercentage = ((price - discountedPrice) / price) * 100;
                card.setPercentage(String.format("%.2f", discountPercentage));
            } catch (NumberFormatException e) {
                card.setPercentage(null); 
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("action", action); 
        result.put("card", card);
        result.put("number",number);
        result.put("what",what);
        result.put("edit",edit);

        return result;
    }



    public List<Card> getAllProducts(Product product){
        List<Card> cards=new ArrayList<>();
        
        Card card=new Card();
        card.setBrand(product.getBrand());
        card.setColor(product.getColor());
        card.setImage(product.getImageUrl());
        card.setPrice("1200");
        card.setDescription("This is a good product");
        card.setDiscountedPrice("800");
        int num=(int)(((Float.parseFloat(card.getPrice())-Float.parseFloat(card.getDiscountedPrice()))/Float.parseFloat(card.getPrice()))*100);
        card.setPercentage(Integer.toString(num));

        for(int i=0;i<6;i++) cards.add(card);

        return cards;
    }
    public List<Card> getAllProducts(int n){
        List<Card> cards=new ArrayList<>();
        Product product =new Product("Aditya", "good product", "Samsung", "black", "https://png.pngtree.com/thumb_back/fh260/background/20230610/pngtree-cute-cuteness-wallpaper-image_2935345.jpg", "black", "2", cards,"https://png.pngtree.com/thumb_back/fh260/background/20230610/pngtree-cute-cuteness-wallpaper-image_2935345.jpg");
        
        
        Card card=new Card();
        card.setBrand(product.getBrand());
        card.setColor(product.getColor());
        card.setImage(product.getImageUrl());
        card.setPrice("1200");
        card.setDescription("This is a good product");
        card.setDiscountedPrice("800");
        int num=(int)(((Float.parseFloat(card.getPrice())-Float.parseFloat(card.getDiscountedPrice()))/Float.parseFloat(card.getPrice()))*100);
        card.setPercentage(Integer.toString(num));

        for(int i=0;i<n;i++) cards.add(card);

        return cards;
    }

    public static Product parseProductDetails(String input) {
        Product product = new Product();

        Pattern pattern = Pattern.compile("(title|description|brand|color|background color|sample product)=([a-zA-Z0-9\\s]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1).toLowerCase();
            String value = matcher.group(2).trim();

            switch (key) {
                case "title":
                    product.setTitle(value);
                    break;
                case "description":
                    product.setDescription(value);
                    break;
                case "brand":
                    product.setBrand(value);
                    break;
                case "color":
                    product.setColor(value);
                    break;
                case "background color":
                    product.setBackgroundColor(value);
                    break;
                case "sample product":
                    product.setNumberOfCards(value);
                    break;
            }
        }

        return product;  
    }
    
}
