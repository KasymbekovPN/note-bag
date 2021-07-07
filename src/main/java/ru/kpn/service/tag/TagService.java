package ru.kpn.service.tag;

import org.bson.types.ObjectId;
import ru.kpn.model.tag.TagEntity;

import java.util.List;
import java.util.Optional;

public interface TagService {
    void save(TagEntity entity);
    void deleteAll();
    void deleteById(ObjectId id);
    void deleteByName(String name);
    Optional<TagEntity> getById(ObjectId id);
    List<TagEntity> getAll();
    List<TagEntity> getByName(String name);
}
