package ru.kpn.service.userProfile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kpn.bot.state.NPBotState;
import ru.kpn.model.userProfile.UserProfileEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class UserProfileServiceTest {

    private static final Long ID = 123L;
    private static final String FIRST_NAME = "Some first name";
    private static final String LAST_NAME = "Some last name";
    private static final String USER_NAME = "Some user name";
    private static final String LANG_CODE = "Some lang code";
    private static final int STATE = NPBotState.UNKNOWN.getId();

    @Autowired
    private UserProfileService service;

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
        service.save(entity);
    }

    @Test
    void shouldGetById() {
        Optional<UserProfileEntity> maybeEntity = service.getById(ID);
        assertThat(maybeEntity).isPresent();
        assertThat(maybeEntity.get().getId()).isEqualTo(ID);
    }

    @Test
    void shouldDeleteById() {
        service.deleteById(ID);
        Optional<UserProfileEntity> maybeEntity = service.getById(ID);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldDeleteAll() {
        service.deleteAll();
        final Optional<UserProfileEntity> maybeEntity = service.getById(ID);
        assertThat(maybeEntity).isEmpty();
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }
}
