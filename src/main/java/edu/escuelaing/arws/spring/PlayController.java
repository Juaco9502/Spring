package edu.escuelaing.arws.spring;

import java.util.Random;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
public class PlayController {
    private String myGuess = "Ninguno";
    private String randomNumber;
    private String resultPicas = "";
    private String resultFamas = "";
    private boolean won = false;

	
    public static void main(String[] args) {
        SpringApplication.run(PlayController.class, args);
    }
    
    public PlayController() {
        randomNumber=generateRandomNumber();             
    }
    
    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("picas", resultPicas);
        model.addAttribute("famas", resultFamas);
        model.addAttribute("won", won);
        model.addAttribute("randomNumber", randomNumber);

        return "index";
    }

    
    @RequestMapping(value = "/", method = RequestMethod.POST, params = { "startGame" })
    public String post(String myGuess) {       
        this.myGuess=myGuess;
        play();
        return "redirect:/";        
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST, params = { "newGame" })
    public String newGame() {       
        randomNumber=generateRandomNumber();
        myGuess = "Ninguno";
        resultPicas = "";
        resultFamas = "";
        won = false;
        return "redirect:/";        
    }
    
    
    public void play(){
       int picas=0;
       int famas=0;
       if (myGuess!="Ninguno"){
           int counter = 0;
            while(counter < myGuess.length()){
               if(isFamas(counter)){
                   famas++;
               }else if(isPicas(counter)){
                   picas++;
               }
            counter++;
           }
           resultPicas="Picas: "+picas;
           resultFamas="Famas: "+famas;
           if(famas==4){
               won = true;
           }
       }      
    }
    
    public String generateRandomNumber(){
        int randomNumber = new Random().nextInt(9000) + 1000;
        return String.valueOf(randomNumber);
    }
    public boolean isFamas(int position){
        return randomNumber.indexOf(myGuess.charAt(position))== position;
    }
    public boolean isPicas(int position){
        return randomNumber.contains(String.valueOf(myGuess.charAt(position)));
    }

}
