package grp10.game.Services.Implementation;

import grp10.game.Persistence.Entities.Word;
import grp10.game.Persistence.Repositories.WordRepository;
import grp10.game.Services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Word> allWords = wordRepository.findByGuessed(false);
        Collections.shuffle(allWords);
        return allWords.get(0);
    }

    @Override
    public void deleteAll() { wordRepository.deleteAll();}

    @Override
    public boolean isDuplicate(Word word) {
        List<Word> allWords = wordRepository.findAll();
        for(Word w: allWords){
            if(w.getValue().equals(word.getValue())) return true;
        }
        return false;
    }

    @Override
    public void setGuessed(Word word) {
        delete(word);
        word.setGuessed(true);
        save(word);
    }

    @Override
    public void setUnguessed(Word word) {
        wordRepository.delete(word);
        word.setGuessed(false);
        wordRepository.save(word);
    }

    @Override
    public boolean isAllGuessed() {
        List<Word> allWords = wordRepository.findAll();
        for(Word w: allWords){
            if(!w.getGuessed()) return false;
        }
        return true;
    }

    @Override
    public void setAllUnguessed() {
        List<Word> allWords = wordRepository.findAll();
        for(Word w: allWords){
            w.setGuessed(false);
        }
        wordRepository.deleteAll();
        wordRepository.saveAll(allWords);
    }

    @Override
    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    @Override
    public Word findByID(long ID) {
        if (wordRepository.findById(ID).isPresent()) {
            return wordRepository.findById(ID).get();
        }
        return null;//TODO need better return value in case of error
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
