package grp10.game.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {

    @RequestMapping("/")
    public String GameController(){
        //business logic
        // call method in service class
        //add some data to model
        return"home";
    }
}
