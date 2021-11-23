package grp10.game.Services.Implementation;

import grp10.game.Persistence.Entities.Word;
import grp10.game.Persistence.Repositories.WordRepository;
import grp10.game.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImplementation implements WordService {
    private WordRepository wordRepository;

    @Autowired
    public WordServiceImplementation(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Word getWord() {
        return wordRepository.findAll();
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
        return wordRepository.findById(ID);
    }

    @Override
    public Word save(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public void delete(Word word) {
        wordRepository.delete(word);
    }
}
