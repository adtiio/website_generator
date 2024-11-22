// package com.trial.controller;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.trial.model.Card;
// import com.trial.model.Edit;
// import com.trial.model.EditProduct;
// import com.trial.model.Page;
// import com.trial.model.Product;
// import com.trial.model.RemoveProduct;

// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;



// @Controller
// public class TempController {

//     Page prod;

//     @GetMapping("/")
//     public String input() {
//         return "inputform";
//     }

//     @PostMapping("/response")
//     public String outputResponse(@ModelAttribute Page page, Model model) {
       
//         prod = page;
        
//         prod.setCards(getAllProducts());
   
//         model.addAttribute("product", prod);
//         return "output";
//     }

//     @PostMapping("/edit")
//     public String editHandler(@ModelAttribute Edit edit, RedirectAttributes redirectAttributes) {
//         if(!edit.getColor().equals("#000000")) prod.setColor(edit.getBackgroundColor());
//         if(!edit.getBackgroundColor().equals("#000000")) prod.setColor(edit.getBackgroundColor());
//         if(!edit.getColor().equals("#000000")) for(Card p : prod.getCards()) p.setColor(edit.getColor());
        
//         redirectAttributes.addFlashAttribute("product", prod);
//         return "redirect:/response";
//     }

//     @GetMapping("/response")
//     public String output(Model model) {
        
//         model.addAttribute("product", prod);
//         return "/output";
//     }

//     public List<Card> getAllProducts(){
//         List<Card> cards=new ArrayList<>();
        
//         Card card=new Card();
//         card.setBrand("Sample");
//         card.setColor("blue");
//         card.setImage("https://1734811051.rsc.cdn77.org/data/images/full/418653/deep-dream-generator.jpg");
//         card.setPrice("1200");
//         card.setDiscountedPrice("1000");
//         int discount=Integer.parseInt(card.getDiscountedPrice());
//         int originalPrice=Integer.parseInt(card.getPrice());
//         int percent=(discount/originalPrice)*100;
//         card.setDiscountedPrice(Integer.toString(percent));

//         for(int i=0;i<6;i++) cards.add(card);

//         return cards;
//     }

//     @PostMapping("/addProduct")
//     public String addProduct(@ModelAttribute Card card, RedirectAttributes redirect){
//         int discount=Integer.parseInt(card.getDiscountedPrice());
//         int originalPrice=Integer.parseInt(card.getPrice());
//         int percent=(discount/originalPrice)*100;
//         card.setDiscountedPrice(Integer.toString(percent));

//         prod.getCards().add(card);
        
//         redirect.addFlashAttribute("product",prod);
//         return "redirect:/response";

//     }

//     @PostMapping("/editProduct")
//     public String editProduct(@ModelAttribute EditProduct product,RedirectAttributes redirect) {
        
//         Card card=new Card();
//         card.setBrand(product.getBrand());
//         card.setColor(product.getColor());
//         card.setPrice(product.getPrice());
//         card.setDiscountedPrice(card.getDiscountedPrice());
//         card.setImage(product.getImage());

//         int discount=Integer.parseInt(card.getDiscountedPrice());
//         int originalPrice=Integer.parseInt(card.getPrice());
//         int percent=(discount/originalPrice)*100;
//         card.setDiscountedPrice(Integer.toString(percent));
        
//         System.out.println(product.getIdx());

//         prod.getCards().set(product.getIdx()-1, card);
//         redirect.addFlashAttribute("product", prod);
//         return "redirect:/response";
//     }

//     @PostMapping("/removeProduct")
//     public String removeProduct(@ModelAttribute RemoveProduct product,RedirectAttributes redirect) {

//         prod.getCards().remove(product.getIdx()-1);
//         redirect.addFlashAttribute("product", prod);
//         return "redirect:/response";
//     }

//     @PostMapping("/nextPage")
//     public String viewProductDetails(@RequestParam("index") int index,Model model) {
    
//         Card card = prod.getCards().get(index);
//         System.out.println(index+" "+card);

//         model.addAttribute("card", card);



//         return"details";
//     }

    
// }
