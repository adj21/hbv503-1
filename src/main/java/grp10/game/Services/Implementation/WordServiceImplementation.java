package grp10.game.Services.Implementation;

import grp10.game.Persistence.Entities.Word;
import grp10.game.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImplementation implements WordService {
    //have an instance of/a reference to the repository when JPA is up so javaspring can autowire it
    private List<Word> wordRepository = new ArrayList<>();//temporary dummy repo
    private int id_counter = 0;//counter to create unique ID for all words of the database

    @Autowired
    public WordServiceImplementation() {
        //create 4 random words, to remove when JPA added
        wordRepository.add(new Word("flower"));
        wordRepository.add(new Word("bird"));
        wordRepository.add(new Word("squid"));
        wordRepository.add(new Word("glass"));
        //add manually ID until JPA gives it automatically
        for(Word w: wordRepository){
            w.setID(id_counter);
            id_counter++;
        }

    }

    @Override
    public Word getWord() {
        for(Word w: wordRepository){
            if(!w.isGuessed()) return w;
        }
        return null;
    }

    @Override
    public boolean isDuplicate(Word word) {
        for(Word w: wordRepository){
            if(w.getValue().equals(word.getValue())) return false;
        }
        return true;
    }

    @Override
    public void setGuessed(Word word) {
        word.setGuessed(true);
    }

    @Override
    public void setUnguessed(Word word) {
        word.setGuessed(false);
    }

    @Override
    public boolean isAllGuessed() {
        for(Word w: wordRepository){
            if(!w.isGuessed()) return false;
        }
        return true;
    }

    @Override
    public void setAllUnguessed() {
        for(Word w: wordRepository){
            w.setGuessed(false);
        }
    }

    @Override
    public List<Word> findAll() {
        return wordRepository;
    }

    @Override
    public Word findByID(long ID) {
        for(Word w: wordRepository){
            if(w.getID() == ID) return w;
        }
        return null;
    }

    @Override
    public Word save(Word word) {
        word.setID(id_counter);
        id_counter++;
        wordRepository.add(word);
        return word;
    }

    @Override
    public void delete(Word word) {
        wordRepository.remove(word);
    }
}
