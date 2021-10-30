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
    public GameController(WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/")
    public String homePage(Model model){
        //business logic
        // call method in service class
        List<Word> allWords = wordService.findAll();
        //add some data to model
        model.addAttribute("words", allWords);
        return"home";
    }

    @RequestMapping(value="/startgame", method = RequestMethod.GET) public String startGame(HttpSession session, Model model) {
        Game game= new Game();
        //game = (Game) session.getAttribute("game");
        //game.incrementCurrentRound();
        session.setAttribute("game", game);
        //session.setAttribute("game", game);
        return "formTeams";
    }

    @RequestMapping(value="/showturn", method = RequestMethod.GET) public String displayTurn(HttpSession session, Model model) {
        //Game game = new Game();
        //game = (Game) session.getAttribute("game");
        //game.incrementCurrentRound();
        //session.setAttribute("game", game);
        return "turn";
    }

    @RequestMapping(value="/addword", method = RequestMethod.GET) public String addWordForm(Word word) {
        return "newWord";
    }

    @RequestMapping(value="/addword", method = RequestMethod.POST) public String addWord(Word word, BindingResult result, Model model) {
        if(result.hasErrors()) return "newWord";
        wordService.save(word);
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET) public String deleteWord(@PathVariable("id") long id, Model model) {
        Word wordToDelete = wordService.findByID(id);
        wordService.delete(wordToDelete);
        return "redirect:/";
    }

}
