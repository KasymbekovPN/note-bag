package ru.kpn.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kpn.model.note.NoteEntity;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, ObjectId> {
    List<NoteEntity> findByUserId(Integer userId);
    List<NoteEntity> findByName(String name);
    void deleteByUserId(Integer userId);
    void deleteByName(String name);
}
