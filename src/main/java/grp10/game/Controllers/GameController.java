package grp10.game.Controllers;

import grp10.game.Persistence.Entities.Game;
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
     * @param model
     * @return home html template
     **/
    @RequestMapping("/")
    public String homePage(Model model){
        // call method (findAll) in service class (to get all words from database)
        List<Word> allWords = wordService.findAll();
        //add data (all words in the database) to model
        model.addAttribute("words", allWords);
        return"home";
    }

    /**
     * method called when clicking on "play!" link to start the game
     * @param session
     * @param model that stores all words
     * @return formTeams html template
     **/
    @RequestMapping(value="/startgame", method = RequestMethod.GET) public String startGame(HttpSession session, Model model) {
        //business logic
        Game game = new Game();
        session.setAttribute("game", game); //stores the game object on the session
        return "formTeams";
    }

    /**
     * method called when starting a round
     * @param session that stores the game object
     * @param model that stores all words
     * @return round html template
     **/
    @RequestMapping(value="/round", method = RequestMethod.GET) public String displayRound(HttpSession session, Model model) {
        //business logic
        Game game = (Game) session.getAttribute("game");
        game.incrementCurrentRound(); //adds +1 to the CurrentRound attribute of the game (TODO should call method from service class do to this?)
        //add game to the session (TODO should be added to the model instead? or also?)
        session.setAttribute("game", game);
        return "round";
    }

    /**
     * GET method for url address of the page to add words to database
     * @param word (TODO not sure this should be here actually)
     * @return newWord html template (to enter a word, TODO should be 5 words)
     **/
    @RequestMapping(value="/addword", method = RequestMethod.GET) public String addWordForm(Word word) {
        return "newWord";
    }

    /**
     * POST method called when submitting form in newWord template
     * @param word to add to database (should be 5)
     * @param result TODO should tell us if there are errors in the form submitted, check newWord template
     * @return redirects to "/" URL (so calls homePage method), or returns newWord template if an error is caught in the form
     **/
    @RequestMapping(value="/addword", method = RequestMethod.POST) public String addWord(Word word, BindingResult result, Model model) {
        if(result.hasErrors()) return "newWord"; //reset page if error in form
        wordService.save(word); //otherwise save word
        return "redirect:/";
    }

    /**
     * method to delete word from database, might not be needed!
     * @param id of the word to delete
     * @param model that stores all words
     * @return redirects to "/" URL (so calls homePage method)
     **/
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET) public String deleteWord(@PathVariable("id") long id, Model model) {
        Word wordToDelete = wordService.findByID(id); //finds word to delete in the database
        wordService.delete(wordToDelete); //metod from service class to delete word
        return "redirect:/";
    }

}
