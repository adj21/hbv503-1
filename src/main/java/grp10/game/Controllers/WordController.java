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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WordController {
    private WordService wordService;

    @Autowired
    /**
     * constructor for class GameController
     * @param wordService WordService instance that the controller will use to call methods.
     **/
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * POST method called when starting a round
     * @param session that stores the game object
     * @return redirects to displayRound GET method (to display page for the round rules)
     **/
    @RequestMapping(value="/skip/{id}", method = RequestMethod.GET) public String skip(HttpSession session, Model model) {
        //Word wordToSkip = wordService.findByID(id);
        //wordService.setUnguessed(wordToSkip); TODO should have a skipped marker so it doesn't come up in the turn again
        //create turn and add word to it
        //Game game = (Game) session.getAttribute("game");
        //Turn turn = game.getRounds().getTurn//get the current round with currentRound from session and curent turn?
        Word word = wordService.getWord();
        //List<Word> turnWords = turn.getWords();
        //turnWords.add(word);
        //turn.setWords(turnWords);
        model.addAttribute("word", word);
        return "turn";//TODO need to figure out how to change word on page without restarting timer, and how to not stay on "skip" address
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
}
