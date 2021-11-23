package grp10.game.Controllers;

import grp10.game.Persistence.Entities.Game;
import grp10.game.Persistence.Entities.Round;
import grp10.game.Persistence.Entities.Turn;
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
import java.util.ArrayList;
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
        wordService.deleteAll();
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
        List<Word> allWords = wordService.findAll();
        model.addAttribute("words", allWords);
        return "newWord";
    }

    /**
     * POST method called when starting a round
     * @param session that stores the game object
     * @return redirects to displayRound GET method (to display page for the round rules)
     **/
    @RequestMapping(value="/startRound", method = RequestMethod.POST) public String startRound(HttpSession session) {
        //business logic
        Game game = (Game) session.getAttribute("game");//get game from session
        game.incrementCurrentRound(); //adds +1 to the CurrentRound attribute of the game (TODO should implement and call method from service class do to this, and have different page for each round)
        //create round object from current round number, retrieve list of rounds from game and add new round to it
        Round round = new Round(game.getCurrentRound());
        List<Round> rounds = game.getRounds();
        rounds.add(round);
        game.setRounds(rounds);//update game object with new round
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
     * POST method called when starting a turn
     * @param session that stores the game object
     * @param model to transfer the word to the view
     * @return turn html template
     **/
    @RequestMapping(value="/startTurn", method = RequestMethod.POST) public String displayTurn(HttpSession session, Model model) {
        //get playing team from session
        Game game = (Game) session.getAttribute("game");
        boolean team = game.isCurrentTeam();
        //create turn and add word to it
        Turn turn = new Turn(team);
        Word word = wordService.getWord();
        List<Word> turnWords = new ArrayList<Word>();
        turnWords.add(word);
        turn.setWords(turnWords);
        model.addAttribute("word", word);
        return "turn";//display turn template
    }

}
