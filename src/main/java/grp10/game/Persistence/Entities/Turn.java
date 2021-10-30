package grp10.game.Persistence.Entities;

import java.time.LocalTime;
import java.util.List;

public class Turn {
    private boolean team;
    private int turnNumber;
    private LocalTime turnStartTime;
    private List<Word> words;

    public Turn(boolean team, int turnNumber, LocalTime turnStartTime) {
        this.team = team;
        this.turnNumber = turnNumber;
        this.turnStartTime = turnStartTime;
    }

    public boolean isTeam() {
        return team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public LocalTime getTurnStartTime() {
        return turnStartTime;
    }

    public void setTurnStartTime(LocalTime turnStartTime) {
        this.turnStartTime = turnStartTime;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
