package ru.kpn.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserProfileRepository extends MongoRepository<UserProfileEntity, ObjectId> {
    List<UserProfileEntity> findByUserId(Integer userId);
    List<UserProfileEntity> findByFirstName(String firstName);
    List<UserProfileEntity> findByLastName(String lastName);
    List<UserProfileEntity> findByUserName(String userName);
    List<UserProfileEntity> findByLanguageCode(String langCode);
    List<UserProfileEntity> findByIsBot(boolean isBot);
    List<UserProfileEntity> findByCanJoinGroups(boolean canJoinGroups);
    List<UserProfileEntity> findByCanReadAllGroupMessages(boolean canReadAllGroupMessages);
    List<UserProfileEntity> findBySupportsInlineQueries(boolean supportsInlineQueries);
    List<UserProfileEntity> findByState(int state);

    void deleteByUserId(Integer userId);
    void deleteByFirstName(String firstName);
    void deleteByLastName(String lastName);
    void deleteByUserName(String userName);
}
