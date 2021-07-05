package ru.kpn.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing of UserRepository")
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class UserProfileRepositoryTest {

    private static final Integer ID = 123;
    private static final String FIRST_NAME = "Some first name";
    private static final String LAST_NAME = "Some last name";
    private static final String USER_NAME = "Some user name";
    private static final String LANG_CODE = "Some lang code";
    private static final boolean IS_BOT = true;
    private static final boolean CAN_JOIN_GROUPS = true;
    private static final boolean CAN_READ_ALL_GROUP_MESSAGES = true;
    private static final boolean SUPPORTS_INLINE_QUERIES = true;
    private static final int STATE = 12345;

    @Autowired
    private UserProfileRepository repository;

    @BeforeEach
    void setUp() {
        UserProfileEntity entity = UserProfileEntity.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .userName(USER_NAME)
                .languageCode(LANG_CODE)
                .isBot(IS_BOT)
                .canJoinGroups(CAN_JOIN_GROUPS)
                .canReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES)
                .supportsInlineQueries(SUPPORTS_INLINE_QUERIES)
                .state(STATE)
                .build();
        repository.save(entity);
    }

    @Test
    void shouldCheckSave() {
        Optional<UserProfileEntity> maybeEntity = repository.findById(ID);
        assertThat(maybeEntity).isPresent();
        assertThat(maybeEntity.get().getId()).isEqualTo(ID);
    }

    @Test
    void shouldFoundByFirstName() {
        List<UserProfileEntity> entities = repository.findByFirstName(FIRST_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getFirstName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldFoundByLastName() {
        List<UserProfileEntity> entities = repository.findByLastName(LAST_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldFoundByUserName() {
        List<UserProfileEntity> entities = repository.findByUserName(USER_NAME);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getUserName()).isEqualTo(USER_NAME);
    }

    @Test
    void shouldFoundByLanguageCode() {
        List<UserProfileEntity> entities = repository.findByLanguageCode(LANG_CODE);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getLanguageCode()).isEqualTo(LANG_CODE);
    }

    @Test
    void shouldFoundByIsBot() {
        List<UserProfileEntity> entities = repository.findByIsBot(IS_BOT);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getIsBot()).isEqualTo(IS_BOT);
    }

    @Test
    void shouldFoundByCanJoinGroups() {
        List<UserProfileEntity> entities = repository.findByCanJoinGroups(CAN_JOIN_GROUPS);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getCanJoinGroups()).isEqualTo(CAN_JOIN_GROUPS);
    }

    @Test
    void shouldFoundByCanReadAllGroupMessages() {
        List<UserProfileEntity> entities = repository.findByCanReadAllGroupMessages(CAN_READ_ALL_GROUP_MESSAGES);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getCanReadAllGroupMessages()).isEqualTo(CAN_READ_ALL_GROUP_MESSAGES);
    }

    @Test
    void shouldFoundBySupportsInlineQueries() {
        List<UserProfileEntity> entities = repository.findBySupportsInlineQueries(SUPPORTS_INLINE_QUERIES);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getSupportsInlineQueries()).isEqualTo(SUPPORTS_INLINE_QUERIES);
    }

    @Test
    void shouldFoundByState() {
        List<UserProfileEntity> entities = repository.findByState(STATE);
        assertThat(entities.size()).isEqualTo(1);
        assertThat(entities.get(0).getState()).isEqualTo(STATE);
    }

    @Test
    void shouldDeleteById() {
        repository.deleteById(ID);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @Test
    void shouldDeleteByFirstName() {
        repository.deleteByFirstName(FIRST_NAME);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @Test
    void shouldDeleteByLastName() {
        repository.deleteByLastName(LAST_NAME);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @Test
    void shouldDeleteByUserName() {
        repository.deleteByUserName(USER_NAME);
        assertThat(collectionIsEmpty()).isTrue();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    private boolean collectionIsEmpty() {
        List<UserProfileEntity> entities = repository.findAll();
        return entities.isEmpty();
    }
}
