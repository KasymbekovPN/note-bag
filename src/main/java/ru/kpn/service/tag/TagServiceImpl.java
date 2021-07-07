package ru.kpn.service.tag;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.kpn.model.tag.TagEntity;
import ru.kpn.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    @Override
    public void save(TagEntity entity) {
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
        log.info("Entity is id '{}' is deleted", id);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
        log.info("Entities with name '{}' is deleted", name);
    }

    @Override
    public Optional<TagEntity> getById(ObjectId id) {
        Optional<TagEntity> maybeEntity = repository.findById(id);
        log.info("Entity is {} found by id {}", maybeEntity.isPresent() ? "" : "not", id);
        return maybeEntity;
    }

    @Override
    public List<TagEntity> getAll() {
        List<TagEntity> entities = repository.findAll();
        log.info("Found {} entities", entities.size());
        return entities;
    }

    @Override
    public List<TagEntity> getByName(String name) {
        List<TagEntity> entities = repository.findByName(name);
        log.info("Found {} entities with field name '{}'", entities.size(), name);
        return entities;
    }
}
