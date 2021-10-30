package grp10.game.Persistence.Entities;

import java.util.List;

public class Round {
    private int roundNumber;
    private int teamOneResults;
    private int teamTwoResults;
    private List<Turn> turns;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.teamOneResults = 0;
        this.teamTwoResults = 0;
    }

    public int getRoundNumber() {

        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {

        this.roundNumber = roundNumber;
    }

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

    public List<Turn> getTurns() {

        return turns;
    }

    public void setTurns(List<Turn> turns) {

        this.turns = turns;
    }
}
