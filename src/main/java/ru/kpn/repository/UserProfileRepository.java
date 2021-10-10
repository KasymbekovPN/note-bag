package ru.kpn.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kpn.model.userProfile.UserProfileEntity;

public interface UserProfileRepository extends MongoRepository<UserProfileEntity, Long> {
}
