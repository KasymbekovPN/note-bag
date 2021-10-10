package ru.kpn.service.userProfile;

import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.Optional;

public interface UserProfileService {
    void save(UserProfileEntity entity);
    void deleteById(Long id);
    void deleteAll();
    Optional<UserProfileEntity> getById(Long id);
}
