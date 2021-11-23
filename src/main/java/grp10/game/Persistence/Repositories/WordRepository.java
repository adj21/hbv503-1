package grp10.game.Persistence.Repositories;

import grp10.game.Persistence.Entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Word save(Word word);
    void delete(Word word);

    List<Word> findAll();
    List<Word> findByID(long ID);
}
