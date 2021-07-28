package ru.kpn.service.note;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpn.bpp.InjectLogger;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.model.note.NoteEntity;
import ru.kpn.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @InjectLogger
    private Logger<CustomizableLogger.LogLevel> log;

    @Override
    public void save(NoteEntity entity) {
        repository.save(entity);
        log.info("Entity is saved : {}", entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
        log.info("All entities is deleted");
    }

    @Override
    public void deleteById(ObjectId id) {
        repository.deleteById(id);
        log.info("Entity with id {} is deleted", id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
        log.info("Entities with name '{}' is deleted", name);
    }

    @Override
    public Optional<NoteEntity> getById(ObjectId id) {
        Optional<NoteEntity> maybeEntity = repository.findById(id);
        log.info("Entity is {} found by id {}", maybeEntity.isPresent() ? "" : "not", id);
        return maybeEntity;
    }

    @Override
    public List<NoteEntity> getAll() {
        List<NoteEntity> entities = repository.findAll();
        log.info("Call method getAll; found {} entities", entities.size());
        return entities;
    }

    @Override
    public List<NoteEntity> getByName(String name) {
        List<NoteEntity> entities = repository.findByName(name);
        log.info("Found {} entities with field name '{}'", entities.size(), name);
        return entities;
    }
}
