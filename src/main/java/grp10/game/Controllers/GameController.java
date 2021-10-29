package grp10.game.Controllers;

import grp10.game.Persistence.Entities.Word;
import grp10.game.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GameController {
    private WordService wordService;

    @Autowired
    public GameController(WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/")
    public String startPage(Model model){
        //business logic
        // call method in service class
        List<Word> allWords = wordService.findAll();
        //add some data to model
        model.addAttribute("words", allWords);
        return"home";
    }
}
