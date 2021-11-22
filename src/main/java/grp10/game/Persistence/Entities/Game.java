package grp10.game.Persistence.Entities;

import java.util.List;

public class Game {
    private int teamOneResults;
    private int teamTwoResults;
    private int currentRound;
    private List<Round> rounds;
    private int playerTotal;


    public Game() {
        this.teamOneResults = 0;
        this.teamTwoResults = 0;
        this.currentRound = 0;
        this.playerTotal = 0;
    }

    public List<Round> getRounds() {
        return this.rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public int getPlayerTotal() { return this.playerTotal; }

    public void setPlayerTotal(int playerTotal) { this.playerTotal = playerTotal; }

    public int getTeamOneResults() {
        return teamOneResults;
    }

    public void setTeamOneResults(int teamOneResults) {
        this.teamOneResults = teamOneResults;
    }

    public int getTeamTwoResults() {
        return teamTwoResults;
    }

    public void setTeamTwoResults(int teamTwoResults) {
        this.teamTwoResults = teamTwoResults;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void incrementCurrentRound() {
        this.currentRound ++;
    }

    public boolean isTie() {
        if (this.teamOneResults == this.teamTwoResults) return true;
        return false;
    }
}
