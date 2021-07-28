package ru.kpn.service.userProfile;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpn.bpp.InjectLogger;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.model.userProfile.UserProfileEntity;
import ru.kpn.repository.UserProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository repository;

    @InjectLogger
    private Logger<CustomizableLogger.LogLevel> log;

    @Override
    public void save(UserProfileEntity entity) {
        repository.save(entity);
        log.info("Entity is saved : {}", entity);
    }

    @Override
    public void deleteById(ObjectId id) {
        repository.deleteById(id);
        log.info("Entity with id {} is deleted", id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
        log.info("All entities is deleted");
    }

    @Override
    public void deleteByUserId(Integer userId) {
        repository.deleteByUserId(userId);
        log.info("Entities by userId '{}' is deleted", userId);
    }

    @Override
    public void deleteByFirstName(String firstName) {
        repository.deleteByFirstName(firstName);
        log.info("Entities by firstName '{}' is deleted", firstName);
    }

    @Override
    public void deleteByLastName(String lastName) {
        repository.deleteByLastName(lastName);
        log.info("Entities by lastName '{}' is deleted", lastName);
    }

    @Override
    public void deleteByUserName(String userName) {
        repository.deleteByUserName(userName);
        log.info("Entities by userName '{}' is deleted", userName);
    }

    @Override
    public Optional<UserProfileEntity> getById(ObjectId id) {
        final Optional<UserProfileEntity> maybeEntity = repository.findById(id);
        log.info("Entity is {} found by id {}", maybeEntity.isPresent() ? "" : "not", id);
        return maybeEntity;
    }

    @Override
    public List<UserProfileEntity> getAll() {
        List<UserProfileEntity> entities = repository.findAll();
        log.info("Call method getAll; found {} entities", entities.size());
        return entities;
    }

    @Override
    public List<UserProfileEntity> getByUserId(Integer userId) {
        List<UserProfileEntity> entities = repository.findByUserId(userId);
        log.info("Found {} entities with field userId '{}'", entities.size(), userId);
        return entities;
    }

    @Override
    public List<UserProfileEntity> getByFirstName(String firstName) {
        List<UserProfileEntity> entities = repository.findByFirstName(firstName);
        log.info("Found {} entities with field firstName '{}'", entities.size(), firstName);
        return entities;
    }

    @Override
    public List<UserProfileEntity> getByLastName(String lastName) {
        List<UserProfileEntity> entities = repository.findByLastName(lastName);
        log.info("Found {} entities with field lastName '{}'", entities.size(), lastName);
        return entities;
    }

    @Override
    public List<UserProfileEntity> getByUserName(String userName) {
        List<UserProfileEntity> entities = repository.findByUserName(userName);
        log.info("Found {} entities with field userName '{}'", entities.size(), userName);
        return entities;
    }
}
