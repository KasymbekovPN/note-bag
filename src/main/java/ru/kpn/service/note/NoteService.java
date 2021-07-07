package ru.kpn.service.note;

import org.bson.types.ObjectId;
import ru.kpn.model.note.NoteEntity;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    void save(NoteEntity entity);
    void deleteAll();
    void deleteById(ObjectId id);
    void deleteByName(String name);
    Optional<NoteEntity> getById(ObjectId id);
    List<NoteEntity> getAll();
    List<NoteEntity> getByName(String name);
}
