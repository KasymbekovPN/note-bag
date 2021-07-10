package ru.kpn.service.userProfile;

import org.bson.types.ObjectId;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    void save(UserProfileEntity entity);
    void deleteById(ObjectId id);
    void deleteAll();
    void deleteByUserId(Integer userId);
    void deleteByFirstName(String firstName);
    void deleteByLastName(String lastName);
    void deleteByUserName(String userName);
    Optional<UserProfileEntity> getById(ObjectId id);
    List<UserProfileEntity> getAll();
    List<UserProfileEntity> getByUserId(Integer userId);
    List<UserProfileEntity> getByFirstName(String firstName);
    List<UserProfileEntity> getByLastName(String lastName);
    List<UserProfileEntity> getByUserName(String userName);
}
