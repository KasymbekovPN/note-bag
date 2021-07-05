package ru.kpn.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kpn.model.tag.TagEntity;

import java.util.List;

public interface TagRepository extends MongoRepository<TagEntity, ObjectId> {
    List<TagEntity> findByName(String name);
    void deleteByName(String name);
}
