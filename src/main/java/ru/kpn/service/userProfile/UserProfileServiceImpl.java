package ru.kpn.service.userProfile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpn.model.userProfile.UserProfileEntity;
import ru.kpn.repository.UserProfileRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository repository;

    @Override
    public void save(UserProfileEntity entity) {
        repository.save(entity);
        log.info("Entity is saved : {}", entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        log.info("Entity with id {} is deleted", id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
        log.info("All entities is deleted");
    }

    @Override
    public Optional<UserProfileEntity> getById(Long id) {
        Optional<UserProfileEntity> maybeEntity = repository.findById(id);
        log.info("Entity is {} found by id {}", maybeEntity.isPresent() ? "" : "not", id);
        return maybeEntity;
    }
}
