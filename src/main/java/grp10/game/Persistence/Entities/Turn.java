package grp10.game.Persistence.Entities;

import java.time.LocalTime;
import java.util.List;

public class Turn {
    private boolean team;
    //private int turnNumber;
    private List<Word> words;

    public Turn(boolean team/*, int turnNumber,*/) {
        this.team = team;
        //this.turnNumber = turnNumber;
    }

    public boolean isTeam() {
        return team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    /*public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }*/

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
