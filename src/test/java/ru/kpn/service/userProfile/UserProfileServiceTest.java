package ru.kpn.service.userProfile;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class UserProfileServiceTest {

    private static final Integer USER_ID = 123;
    private static final String FIRST_NAME = "Some first name";
    private static final String LAST_NAME = "Some last name";
    private static final String USER_NAME = "Some user name";

    @Autowired
    private UserProfileService service;

    private ObjectId id;

    @BeforeEach
    void setUp() {
        UserProfileEntity entity = UserProfileEntity.builder()
                .userId(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .userName(USER_NAME)
                .build();
        service.save(entity);

        id = entity.getId();
    }

    @Test
    void shouldSave() {
        UserProfileEntity entity = UserProfileEntity.builder().build();
        service.save(entity);
        assertThat(entity.getId()).isNotNull();
    }

    @Test
    void shouldGetById() {
        Optional<UserProfileEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldDeleteById() {
        service.deleteById(id);
        Optional<UserProfileEntity> maybeEntity = service.getById(id);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldFindAll() {
        List<UserProfileEntity> entities = service.getAll();
        assertThat(entities.size()).isNotZero();
    }

    @Test
    void shouldDeleteAll() {
        service.deleteAll();
        List<UserProfileEntity> entities = service.getAll();
        assertThat(entities.size()).isZero();
    }

    @Test
    void shouldGetByUserId() {
        List<UserProfileEntity> entities = service.getByUserId(USER_ID);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getUserId()).isEqualTo(USER_ID);
    }

    @Test
    void shouldDeleteByUserId() {
        service.deleteByUserId(USER_ID);
        List<UserProfileEntity> entities = service.getByUserId(USER_ID);
        assertThat(entities).isEmpty();
    }

    @Test
    void shouldGetByFirstName() {
        List<UserProfileEntity> entities = service.getByFirstName(FIRST_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldDeleteByFirstName() {
        service.deleteByFirstName(FIRST_NAME);
        List<UserProfileEntity> entities = service.getByFirstName(FIRST_NAME);
        assertThat(entities).isEmpty();
    }

    @Test
    void shouldGetByLastName() {
        List<UserProfileEntity> entities = service.getByLastName(LAST_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldDeleteByLastName() {
        service.deleteByLastName(LAST_NAME);
        List<UserProfileEntity> entities = service.getByLastName(LAST_NAME);
        assertThat(entities).isEmpty();
    }

    @Test
    void shouldGetByUserName() {
        List<UserProfileEntity> entities = service.getByUserName(USER_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldDeleteByUserName() {
        service.deleteByUserName(USER_NAME);
        List<UserProfileEntity> entities = service.getByUserName(USER_NAME);
        assertThat(entities).isEmpty();
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }
}
