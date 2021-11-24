package grp10.game.Services;

import grp10.game.Persistence.Entities.Word;

import java.util.List;

public interface WordService {
    Word getWord(); //get next unguessed word in list
    boolean isDuplicate(Word word);
    void setGuessed(Word word);
    void setUnguessed(Word word);
    boolean isAllGuessed();
    void setAllUnguessed();
    List<Word> findAll();
    Word findByID(long ID);
    Word save(Word word);
    void delete(Word word);
    void deleteAll();

}
