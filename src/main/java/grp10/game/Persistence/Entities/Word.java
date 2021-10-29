package grp10.game.Persistence.Entities;

public class Word {
    private long ID;
    private String value;
    private boolean guessed;

    public Word() {
    }

    public Word(String value) {
        this.value = value;
        this.guessed = false;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }
}
