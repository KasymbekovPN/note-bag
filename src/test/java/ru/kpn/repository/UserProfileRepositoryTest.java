package ru.kpn.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class UserProfileRepositoryTest {

    private static final Long ID = 789L;
    private static final String FIRST_NAME = "Some first name";
    private static final String LAST_NAME = "Some last name";
    private static final String USER_NAME = "Some user name";
    private static final String LANG_CODE = "Some lang code";
    private static final int STATE = NPBotState.UNKNOWN.getId();

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
                .state(STATE)
                .build();
        repository.save(entity);
    }

    @Test
    void shouldCheckSave() {
        Optional<UserProfileEntity> maybeEntity = repository.findById(ID);
        assertThat(maybeEntity).isPresent();
        UserProfileEntity entity = maybeEntity.get();
        assertThat(entity.getId()).isEqualTo(ID);
        assertThat(entity.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(entity.getLastName()).isEqualTo(LAST_NAME);
        assertThat(entity.getUserName()).isEqualTo(USER_NAME);
        assertThat(entity.getState()).isEqualTo(STATE);
    }

    @Test
    void shouldDeleteById() {
        repository.deleteById(ID);
        List<UserProfileEntity> entities = repository.findAll();
        assertThat(entities).isEmpty();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }
}
