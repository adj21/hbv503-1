package grp10.game.Controllers;

import grp10.game.Persistence.Entities.Game;
import grp10.game.Persistence.Entities.Word;
import grp10.game.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameController {
    private WordService wordService;

    @Autowired
    /**
     * constructor for class GameController
     * @param wordService WordService instance that the controller will use to call methods.
     **/
    public GameController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * method called when accessing the homepage of the game
     * @return home html template
     **/
    @RequestMapping("/")
    public String homePage(){
        return"home";
    }

    /**
     * GET method called when clicking on "play!" link to start the game in homepage. Initializes game state
     * @param session to add the game attribute
     * @param model to add the game attribute to be have access to the playerTotal attribute of game in the form of formTeams
     * @return formTeams html template (to enter player count)
     **/
    @RequestMapping(value="/startgame", method = RequestMethod.GET) public String startGame(HttpSession session, Model model) {
        //business logic
        Game game = new Game();
        session.setAttribute("game", game); //stores the game object on the session
        model.addAttribute("game",game);//puts the game object on the model to be accessible by the form in formTeams to save the player count.
        session.removeAttribute("wordCount");//remove wordCount in case the user is going back to homepage after playing
        return "formTeams";
    }

    /**
     * POST method called when clicking on "done!" (in formTeams) after entering the number of players,
     * or when clicking on "next!" to let a new player enter their words (sets or updates the attributes game.playerTotal and wordCount of session).
     * @param session to get/update the game attribute
     * @param gameP to get the game object containing the player count if we are coming directly from formTeams template
     * @return redirect to /addword (form to add a word to database)
     **/
    @RequestMapping(value="/addFiveWords", method = RequestMethod.POST) public String startAddWords(Game gameP, HttpSession session) {
        //business logic
        Game game = (Game) session.getAttribute("game");
        if(session.getAttribute("wordCount")!= null) { //wordCount exists, so words have been already entered
            int playerTotal = game.getPlayerTotal(); //retrieve playerTotal from the game object from the session
            if ( (int) session.getAttribute("wordCount") == 0) { //if wordCount is at zero, next player needs to enter their words
                playerTotal--; //lower the number of players left to enter their words
                game.setPlayerTotal(playerTotal); //update playerTotal attribute of game
            }
        }
        else {//if wordCount doesn't exist, no words have been entered yet
            int playerTotal = gameP.getPlayerTotal(); // retrieve playerTotal from the form in formTeams passed as argument of startAddWords
            game.setPlayerTotal(playerTotal);//set the playerTotal attribute of the game for the session
        }
        session.setAttribute("game", game); //update the game object of the session
        int wordCount = 5; //set or restart the wordcount at 5 (player needs to enter all their words)
        session.setAttribute("wordCount", wordCount); // put the wordCount on the session
        return "redirect:/addword";
    }

    /**
     * GET method for url address of the page to add words to database. Puts data on model for the view.
     * @param word object to be filled in newWord form
     * @param model to put playerTotal and wordCount on the model to be displayed
     * @param session to retrieve wordCount and game object
     * @return newWord html template (to enter a word)
     **/
    @RequestMapping(value="/addword", method = RequestMethod.GET) public String addWordForm(Word word, Model model, HttpSession session) {
        model.addAttribute("wordCount", session.getAttribute("wordCount"));//put wordCount on the model to be displayed
        Game game = (Game) session.getAttribute("game");//retrieve game object
        model.addAttribute("playerTotal", game.getPlayerTotal());//put playerTotal on the model to be displayed
        return "newWord";
    }

    /**
     * POST method called when submitting form in newWord template (saves word on database)
     * @param word to add to database (should be 5)
     * @param session to get and update wordCount
     * @param result TODO should tell us if there are errors in the form submitted, check newWord template
     * @return redirects to addword to add next word or to "/" URL (so calls homePage method) if there is an error.
     **/
    @RequestMapping(value="/addword", method = RequestMethod.POST) public String addWord(Word word, HttpSession session, BindingResult result) {
        //if(result.hasErrors()) return "newWord"; //reset page if error in form TODO figure out how result.hasErrors works
        int wordCount = (int) session.getAttribute("wordCount");//get wordCount from session
        Game game = (Game) session.getAttribute("game");
        if((wordCount == 1) && (game.getPlayerTotal() == 1)) {//if the last user was entering the last word
            wordCount=0;//last word was added to database, so wordCount=0
            session.setAttribute("wordCount", wordCount);//update wordCount on session
            game.setPlayerTotal(0);//no more players need to enter words
            session.setAttribute("game", game);//update game object with correct playerTotal
            return "redirect:/addword";//send to /addword to display view prompting the beginning of round 1
        }
        else if(wordCount>=0) {//check that user didn't enter already 5 words
            //TODO check if word already exists in database
            //TODO check that word is not empty string
            wordService.save(word);//save word to database
            wordCount--;//decrement wordCount (since a word is being added to the database)
            session.setAttribute("wordCount", wordCount);//update wordCount on session
            return "redirect:/addword";//send user to add next word
        }
        else  {//if user entered all 5 words already and is trying to add more words (thymeleaf currently prevents this)
            return "redirect:/";//redirect to homepage if player tried to enter more than 5 words
        }
    }

    /**
     * POST method called when starting a round
     * @param session that stores the game object
     * @return redirects to displayRound GET method (to display page for the round rules)
     **/
    @RequestMapping(value="/startRound", method = RequestMethod.POST) public String startRound(HttpSession session) {
        //business logic
        Game game = (Game) session.getAttribute("game");//get game from session
        game.incrementCurrentRound(); //adds +1 to the CurrentRound attribute of the game (TODO should implement and call method from service class do to this)
        session.setAttribute("game", game);//add game to the session
        return "redirect:/round";//display round page
    }

    /**
     * GET method called when viewing the round rules page
     * @param session that stores the game object
     * @param model to transfer the round number to the view
     * @return round html template
     **/
    @RequestMapping(value="/round", method = RequestMethod.GET) public String displayRound(HttpSession session, Model model) {
        Game game = (Game) session.getAttribute("game");
        model.addAttribute("roundNumber", game.getCurrentRound()); //adds round number to the model to be retrieved by the view
        return"round";//display round template
    }

    /**
     * GET method called when viewing the round rules page
     * @param session that stores the game object
     * @param model to transfer the round number to the view
     * @return round html template
     **/
    @RequestMapping(value="/startTurn", method = RequestMethod.POST) public String displayTurn() {
        //TODO intialize the turn object
        //TODO get a word from database with wordService.getword()
        //TODO add that word to the list of words in turn
        return "turn";//display turn template
    }

    /**
     * GET method to delete word from database, might not be needed (unused for now)!
     * @param id of the word to delete
     * @return redirects to "/" URL (so calls homePage method)
     **/
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET) public String deleteWord(@PathVariable("id") long id) {
        Word wordToDelete = wordService.findByID(id); //finds word to delete in the database
        wordService.delete(wordToDelete); //method from service class to delete word
        return "redirect:/";
    }

}
